package com.we.piccategory.mvp;

import java.util.List;

/**
 * Created by 86119 on 2017/3/21.
 */

public interface PicView {

    void loading();

    void show(List<?> data);
}
