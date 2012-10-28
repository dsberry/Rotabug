package com.rotabug.client;

import java.util.ArrayList;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONObject;

public class JSONBuffer extends JavaScriptObject {

	public static final int NOID = -1;

	protected JSONBuffer() {
	}

	public final static native JSONBuffer create() /*-{
		return new Object();
	}-*/;

	public final static native JSONBuffer fromJSON(String jsonString) /*-{
		return JsonUtils.safeEval('('+jsonString+')');
	}-*/;

	public final static native String stringFromJSON(String jsonString) /*-{
		return eval('('+jsonString+')');
	}-*/;

	public final String toJSON() {
		return new JSONObject(this).toString();
	}

	private final native boolean checkKey(String key)/*-{
		if (this[key] == undefined)
			throw "Item '" + key + "' not found in JavaScript object";
	}-*/;

	private final native String getString(String key, int i) /*-{
		return "" + this[key][i];
	}-*/;

	private final native String getString(String key) /*-{
		return "" + this[key];
	}-*/;

	private final native JSONBuffer getObject(String key, int i) /*-{
		return this[key][i];
	}-*/;

	private final native JSONBuffer getObject(String key) /*-{
		return this[key];
	}-*/;

	private final native int getLength(String key) /*-{
		return this[key].length;

	}-*/;

	private final native void makeArray(String key, int length) /*-{
		this[key] = new Array(length);
	}-*/;

	private final native void setI(String key, int val, int i) /*-{
		this[key][i] = val;
	}-*/;

	private final native void setS(String key, String val, int i) /*-{
		this[key][i] = val;
	}-*/;

	private final native void setO(String key, JSONBuffer val, int i) /*-{
		this[key][i] = val;
	}-*/;

	public final String getS(String key) {
		checkKey(key);
		return getString(key);
	}

	public final JSONBuffer getO(String key) {
		checkKey(key);
		return getObject(key);
	}

	public final int getI(String key) {
		checkKey(key);
		return Integer.parseInt(getString(key));
	}

	public final String[] getSA(String key) {
		String[] result;
		checkKey(key);
		int len = getLength(key);
		if (len == 0) {
			result = new String[1];
			result[0] = getString(key);
		} else {
			result = new String[len];
			for (int i = 0; i < len; i++)
				result[i] = getString(key, i);
		}
		return result;
	}

	public final JSONBuffer[] getOA(String key) {
		JSONBuffer[] result;
		checkKey(key);
		int len = getLength(key);
		if (len == 0) {
			result = new JSONBuffer[1];
			result[0] = getObject(key);
		} else {
			result = new JSONBuffer[len];
			for (int i = 0; i < len; i++)
				result[i] = getObject(key, i);
		}
		return result;
	}

	public final int[] getIA(String key) {
		int[] result;
		checkKey(key);
		int len = getLength(key);
		if (len == 0) {
			result = new int[1];
			result[0] = Integer.parseInt(getString(key));
		} else {
			result = new int[len];
			for (int i = 0; i < len; i++)
				result[i] = Integer.parseInt(getString(key, i));
		}
		return result;
	}

	public final ArrayList<String> getSL(String key) {
		ArrayList<String> result;
		checkKey(key);
		int len = getLength(key);
		if (len == 0) {
			result = new ArrayList<String>(1);
			result.add(getString(key));
		} else {
			result = new ArrayList<String>(len);
			for (int i = 0; i < len; i++)
				result.add(getString(key, i));
		}
		return result;
	}

	public final ArrayList<JSONBuffer> getOL(String key) {
		ArrayList<JSONBuffer> result;
		checkKey(key);
		int len = getLength(key);
		if (len == 0) {
			result = new ArrayList<JSONBuffer>(1);
			result.add(getObject(key));
		} else {
			result = new ArrayList<JSONBuffer>(len);
			for (int i = 0; i < len; i++)
				result.add(getObject(key, i));
		}
		return result;
	}

	public final ArrayList<Integer> getIL(String key) {
		ArrayList<Integer> result;
		checkKey(key);
		int len = getLength(key);
		if (len == 0) {
			result = new ArrayList<Integer>(1);
			result.add(Integer.decode(getString(key)));
		} else {
			result = new ArrayList<Integer>(len);
			for (int i = 0; i < len; i++)
				result.add(Integer.decode(getString(key, i)));
		}
		return result;
	}

	public final native void setI(String key, int val) /*-{
		this[key] = val;
	}-*/;

	public final native void setS(String key, String val) /*-{
		this[key] = val;
	}-*/;

	public final native void setO(String key, JSONBuffer val) /*-{
		this[key] = val;
	}-*/;

	public final void setIA(String key, int val[]) {
		int len = val.length;
		makeArray(key, len);
		for (int i = 0; i < len; i++) {
			setI(key, val[i], i);
		}
	}

	public final void setSA(String key, String val[]) {
		int len = val.length;
		makeArray(key, len);
		for (int i = 0; i < len; i++) {
			setS(key, val[i], i);
		}
	}

	public final void setOA(String key, JSONBuffer val[]) {
		int len = val.length;
		makeArray(key, len);
		for (int i = 0; i < len; i++) {
			setO(key, val[i], i);
		}
	}

	public final void setIL(String key, ArrayList<Integer> val) {
		int len = val.size();
		makeArray(key, len);
		for (int i = 0; i < len; i++) {
			setI(key, val.get(i).intValue(), i);
		}
	}

	public final void setSL(String key, ArrayList<String> val) {
		int len = val.size();
		makeArray(key, len);
		for (int i = 0; i < len; i++) {
			setS(key, val.get(i), i);
		}
	}

	public final void setOL(String key, ArrayList<JSONBuffer> val) {
		int len = val.size();
		makeArray(key, len);
		for (int i = 0; i < len; i++) {
			setO(key, val.get(i), i);
		}
	}
}
