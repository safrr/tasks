package com.safronova.api.preserver;

import java.util.List;

public interface DBPreserver {
    <T> void saveData(List<T> objects);
}
