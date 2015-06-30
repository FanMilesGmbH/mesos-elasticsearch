package org.apache.mesos.elasticsearch.common.zookeeper.parser;

import org.apache.mesos.elasticsearch.common.zookeeper.exception.ZKAddressException;
import org.apache.mesos.elasticsearch.common.zookeeper.model.ZKAddress;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Ensures ZooKeeper addresses are formatted properly
 */
public class ZKAddressParser {
    public static final String ZK_PREFIX = "zk://";
    public static final String ZK_PREFIX_REGEX = "^" + ZK_PREFIX + ".*";
    public static final String VALID_ZK_URL = "zk://host1:port1,user:pass@host2:port2/path,.../path";

    public List<ZKAddress> validateZkUrl(final String zkUrl) {
        final List<ZKAddress> zkList = new ArrayList<>();

        // Ensure that string is prefixed with "zk://"
        Matcher matcher = Pattern.compile(ZK_PREFIX_REGEX).matcher(zkUrl);
        if (!matcher.matches()) {
            throw new ZKAddressException(zkUrl);
        }

        // Strip zk prefix and spaces
        String zkStripped = zkUrl.replace(ZK_PREFIX, "").replace(" ", "");

        // Split address by commas
        String[] split = zkStripped.split(",");

        // Validate and add each split
        for (String s : split) {
            zkList.add(new ZKAddress(s));
        }

        // Return list of zk addresses
        return zkList;
    }
}