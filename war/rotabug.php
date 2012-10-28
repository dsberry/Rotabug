<?php
require_once('rb.php');

# ----------------------------------------------------------------------------------------
#  The following values should be the same as the corresponding values in Requester.java

#  The key for the requested Opcode.
define( 'OP_KEY', "OP" );

#  The key for the Request ID that combines the ticket number and session id.
define( 'RID_KEY', "RID" );

#  Opcodes for the functions that can be performed by this script.
define( 'REQUEST_SESSION_ID', 1 );
define( 'KILL_SESSION', 2 );

#  Spins for each character in a randomised string.
define( 'NSPIN', 16 );
$SPINS = array( 5, 6, 11, 11, 3, 13, 5, 7, 13, 4, 10, 8, 7, 4, 5, 12 );

# Secret code indices
define( 'NSECRET', 5 );
$SECRETS = array( 9, 3, 6, 1, 7 );

# ----------------------------------------------------------------------------------------

#  Other local constants
define( 'NO_USER', -1);
define( 'NO_DOC', -1);
define( 'SESSION_TYPE', "Session" );
define( 'LOG_FILE', "/home/dsb/rotabug/php.log" );
define( 'DEAD_TIME', 5);

#  ---------------------------------------------------------------------------------------

$LOGH = NULL;
$LOG_LEVEL = 10;


#  Main entry point...

#  Establish a connection to the database.
R::setup('mysql:host=localhost;dbname=rotabug','root',
           'WhenTheMusicFades');

#  Get the opcode that indicates what function is to be performed by this script.
$opcode = $_POST[OP_KEY];
logEvent( 1, "opcode: $opcode\n" );

#  In the majority of cases the first thing to do is to identify the client,
#  verify that the request is genuinely from that client, and get the
#  information describing the state of the client. However, we cannot do
#  this if the client is requesting a new session (i.e. if this the first
#  request from the client). So handle this case separately.

# Request session id
if( $opcode == REQUEST_SESSION_ID ) {
	toJSON( newSession() );

	#  All other operations requires a valid Request ID to be supplied with the request.
} else {
	$ss = ValidateRID();
	if( $ss !== NULL ) {
		logEvent( 1, "session: $ss->id\n" );

		if( $opcode == KILL_SESSION ) {
			deleteSession( $ss );




		} else if( $opcode == 20 ) {
			toJSON( "HEHE" );



			# Unknown op-code.
		} else {
			header(':', true, 501 );
		}

		# Session validation failed. If we get spurious validation failures, we could
		# ask the client to resend the request, including the secret code. If the secret
		# code matches, then honour the request but start a new session for the client,
		# turning the new session ID to the client.
	} else {
		header(':', true, 401 );
	}
}

if( $LOGH !== NULL ) {
	fclose( $LOGH );
}

# ------------------------------------------------------------------------------------------
#  Functions definitions...

function newSession(){
	global $SECRETS;

	purgeDeadSessions();

	$ss =  R::dispense(SESSION_TYPE);
	$ss->check = mt_rand();  #  Unique random number associated with the session
	$ss->htn = 0;            #  The highest ticket number used so far by this session
	$ss->utn = " ";          #  A space seperated list of unused ticket numbers less than "htn"
	$ss->scode = 0;          #  A secret code for the session, which is not included in most requests
	$ss->user = NO_USER;     #  ID for the logged-in user (NO_USER if not logged in)
	$ss->doc = NO_DOC;       #  ID of the current document being edited in the session
	$ss->created = time();   #  Creation time of the session
	$ss->accessed = $ss->created; #  Last access time for the session
	R::store($ss);

	#  Get an opaque string identifier that identifies this Session.
	$ssid = spin1( $ss->id, $ss->check );

	#  The secret code for the session is the integer formed by reading an agreed set of
	#  characters from the session identifier, and converting from hexadecimal to decimal.
	$a = " ";
	for( $i = 0; $i < NSECRET; $i++ ){
		$a{$i} = $ssid{ $SECRETS[$i] };
	}
	$ss->scode = hexdec( $a );
	R::store($ss);
	logEvent( 1, "New session $ss->id started\n");
	return $ssid;
}

#  Returns a randomised string of hexadecimal characters from which the two supplied integers can
#  be recovered using "unspin1".
function spin1( $int1, $int2 ){
	return spin2( sprintf( "%X",$int1), sprintf( "%X",$int2) );
}

function unspin1( $string ){
	$a = unspin2( $string);
	return array( hexdec( $a[0] ), hexdec( $a[1] ) );
}

#  Returns a randomised string of hexadecimal characters from which the two supplied strings (which
#  should also contain only hexadecimal characters) can be recovered using "unspin2".
function spin2( $s1, $s2 ){
	global $SPINS;
	$len1 = strlen($s1);
	$a = sprintf( "%02X", $len1 ).$s1.$s2;
	$la = strlen($a);
	$ispin = mt_rand(0,15);
	$result = " ";
	$result{0} = dechex($ispin);
	for( $i=0; $i<$la; $i++ ){
		$result{$i+1} = dechex( ( hexdec( $a{$i} ) + $SPINS[$ispin] ) % 16 );
		if( ++$ispin == NSPIN ) $ispin = 0;
	}
	return $result;
}

function unspin2( $s ){
	global $SPINS;
	$la = strlen($s) - 1;
	$ispin = hexdec($s{0});
	$a = " ";
	for( $i=0; $i<$la; $i++ ){
		$b = hexdec($s{$i+1}) - $SPINS[$ispin];
		while( $b < 0 ) $b += 16;
		$a{$i} = dechex($b);
		if( ++$ispin == NSPIN ) $ispin = 0;
	}
	$len1 = hexdec(substr($a,0,2));
	$s1 = substr($a,2,$len1);
	$s2 = substr($a,2+$len1);
	return array($s1,$s2);
}

function toJSON( $value ){
	header('Content-Type: text/javascript');
	header('Cache-Control: no-cache');
	header('Pragma: no-cache');
	$ss = json_encode( $value );
	echo $ss;
}

function validateRID( ){
	$ss = NULL;
	if (isset($_POST[RID_KEY]) ) {
		$a = unspin2( $_POST[RID_KEY] );
		$tno = $a[0];
		$b = unspin1( $a[1] );
		$ss = R::load(SESSION_TYPE, $b[0]);
		if( $ss->check != $b[1] ) {
			logEvent(1,"Bad RID: check no. for session $ss->id is $ss->check but $b[1] was supplied\n");
			$ss = NULL;
    	} else if( !validateTicket( $tno, $ss )) {
			logEvent(1,"Bad RID: ticket $tno has already been used by session $ss->id\n");
			$ss = NULL;
    	} else {
			$ss->accessed = time();
			R::store($ss);
		}
	}
	return $ss;
}

function validateTicket( $tno, $ss ) {
	$result = false;
	if( $tno > $ss->htn ) {
		for( $i = $ss->htn + 1; $i < $tno; $i++ ) {
			$ss->utn .= "$i ";
		}
		$ss->htn = $tno;
		$result = true;
	} else {
		$needle = " $tno ";
		$pos = strpos( $ss->utn, $needle );
		if( $pos !== false ) {
			$ss->utn = substr_replace( $ss->utn, '', $pos, strlen($needle)-1 );
			$result = true;
		}
	}
	return $result;
}

function purgeDeadSessions() {
	foreach( R::findAll(SESSION_TYPE,' ') as $id => $ss ) {
		$delta = time() - $ss->accessed;
		if( $delta > DEAD_TIME ){
			deleteSession( $ss );
		}
	}
}

function deleteSession( $ss ) {
	$delta = time() - $ss->accessed;
	logEvent( 1, "Killing session $ss->id (inactive for $delta seconds)\n");
	R::trash( $ss );
}

function logEvent( $level, $text ) {
	global $LOGH;
	global $LOG_LEVEL;
	if( $LOG_LEVEL >= $level ) {
		if( $LOGH === NULL ) {
			$LOGH = fopen( LOG_FILE, "a" );
			fwrite( $LOGH, "--------------------   " );
			date_default_timezone_set('Europe/London');
			fwrite( $LOGH, date(DATE_ATOM) );
			fwrite( $LOGH, "\n" );
				
		}
		fwrite($LOGH, "$text\n");
	}
}