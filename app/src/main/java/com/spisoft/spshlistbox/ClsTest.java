package com.spisoft.spshlistbox;

import java.util.List;

public class ClsTest {

    private String OptCode ;
    private String OptTitle ;
    private boolean Disabled ;

    public ClsTest(String optCode, String optTitle, boolean disabled){
        this.OptCode = optCode;
        this.OptTitle = optTitle;
        this.Disabled = disabled;
    }

    public boolean isDisabled() {
        return Disabled;
    }

    public void setDisabled(boolean disabled) {
        Disabled = disabled;
    }

    public String getOptCode() {
        return OptCode;
    }

    public void setOptCode(String optCode) {
        OptCode = optCode;
    }

    public String getOptTitle() {
        return OptTitle;
    }

    public void setOptTitle(String optTitle) {
        OptTitle = optTitle;
    }

}
