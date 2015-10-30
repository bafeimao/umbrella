package net.bafeimao.umbrella.support.data;

import java.util.List;

/**
 * Created by Administrator on 2015/10/28.
 */
public class SelectEvent extends DataEntity<Long> {
    private List option1;
    private int scope;

    public List<?> getOption1() {
        return option1;
    }

    public void setOption1(List<?> option1) {
        this.option1 = option1;
    }

    public int getScope() {
        return scope;
    }

    public void setScope(int scope) {
        this.scope = scope;
    }
}
