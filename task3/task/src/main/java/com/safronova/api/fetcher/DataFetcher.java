package com.safronova.api.fetcher;

import java.io.IOException;

public interface DataFetcher {
    Object[] fetch() throws IOException;
}
