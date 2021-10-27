package com.safronova.api.preserver;

import java.util.List;

public interface FilePreserver {
    <T> void preserve(String path, List<T> objects);
}
