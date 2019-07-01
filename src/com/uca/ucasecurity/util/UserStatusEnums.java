package com.uca.ucasecurity.util;

public enum UserStatusEnums {
	
    USER_STATUS_CANCLE(0),
	USER_STATUS_USE(1),
	USER_STATUS_LOCK(2),
	;

	private final int key;

    public int getKey() {
        return key;
    }

    private UserStatusEnums(int key) {
        this.key = key;
    }

    public boolean equalsValue(int value) {
    	UserStatusEnums target = null;
    	UserStatusEnums[] values = values();
        for (UserStatusEnums enumInstance : values) {
            if (enumInstance.getKey() == value) {
                target = enumInstance;
            }
        }
        return this.equals(target);
    }
    
    public String getKeyDescription() {
        if (this != null) {
        	if(this == USER_STATUS_CANCLE) {
        		return "注销";
        	} else if (this == USER_STATUS_LOCK) {
        		return "锁定";
        	} else if (this == USER_STATUS_USE) {
        		return "启用";
        	}
        }
        return "";
    }

    public static String getKeyDescriptionByValue(int key) {
    	UserStatusEnums[] values = values();
    	for(UserStatusEnums enumInstance : values) {
    		if(enumInstance.getKey() == key) {
    			return enumInstance.getKeyDescription();
    		}
    	}
    	
        return "";
    }

    @Override
    public String toString() {
        return String.valueOf(getKey());
    }

    public static final UserStatusEnums valueOfByKey(int key) {
    	UserStatusEnums[] values = values();
        for (UserStatusEnums enumInstance : values) {
            if (enumInstance.equalsValue(key)) {
                return enumInstance;
            }
        }
        //
        return null;
    }
}
