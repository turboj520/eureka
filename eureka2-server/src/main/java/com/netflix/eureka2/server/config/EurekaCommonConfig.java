package com.netflix.eureka2.server.config;

import com.netflix.eureka2.codec.CodecType;
import com.netflix.eureka2.config.ConfigurationNames.RegistryNames;
import com.netflix.eureka2.config.ConfigurationNames.TransportNames;
import com.netflix.eureka2.config.EurekaRegistryConfig;
import com.netflix.eureka2.config.EurekaTransportConfig;
import com.netflix.eureka2.registry.datacenter.LocalDataCenterInfo;
import com.netflix.eureka2.registry.eviction.EvictionStrategyProvider;
import com.netflix.eureka2.registry.eviction.EvictionStrategyProvider.StrategyType;
import com.netflix.eureka2.server.resolver.EurekaEndpointResolvers.ResolverType;
import com.netflix.governator.annotations.Configuration;

/**
 * Define common configurations for all eureka clients and servers
 *
 * @author David Liu
 */
public abstract class EurekaCommonConfig implements EurekaTransportConfig, EurekaRegistryConfig {

    public static final int DEFAULT_SHUTDOWN_PORT = 7700;
    public static final int DEFAULT_ADMIN_PORT = 8077;
    public static final int DEFAULT_HTTP_PORT = 8080;

    public static final long DEFAULT_HEARTBEAT_INTERVAL_MS = 30 * 1000;
    public static final long DEFAULT_CONNECTION_AUTO_TIMEOUT_MS = 30 * 60 * 1000;

    public static final String DEFAULT_CODEC = "Avro";

    public static final long DEFAULT_EVICTION_TIMEOUT = 30000;

    public static final int DEFAULT_DATACENTER_RESOLVE_INTERVAL_SEC = 30;

    @Configuration("eureka.common.writeCluster.resolverType")
    protected String resolverType = ResolverType.Fixed.name();

    @Configuration("eureka.common.writeCluster.serverList")
    protected String[] serverList = {"localhost:12102:12103:12104"};

    // instance info configs
    @Configuration("eureka.instanceInfo.appName")
    protected String appName = "defaultEurekaCluster";

    @Configuration("eureka.instanceInfo.vipAddress")
    protected String vipAddress = "defaultEurekaCluster";

    @Configuration("eureka.services.readCluster.vipAddress")
    protected String readClusterVipAddress;

    @Configuration("eureka.dataCenterInfo.type")
    protected String dataCenterType = LocalDataCenterInfo.DataCenterType.Basic.name();

    @Configuration("eureka.dataCenterInfo.resolveIntervalSec")
    protected int dataCenterResolveIntervalSec = DEFAULT_DATACENTER_RESOLVE_INTERVAL_SEC;

    @Configuration("eureka.services.http.port")
    protected int httpPort = DEFAULT_HTTP_PORT;

    @Configuration("eureka.services.shutdown.port")
    protected int shutDownPort = DEFAULT_SHUTDOWN_PORT;

    @Configuration("netflix.platform.admin.resources.port")
    protected int webAdminPort = DEFAULT_ADMIN_PORT;

    // transport configs
    @Configuration(TransportNames.heartbeatIntervalMsName)
    private long heartbeatIntervalMs = DEFAULT_HEARTBEAT_INTERVAL_MS;

    @Configuration(TransportNames.connectionAutoTimeoutMsName)
    protected long connectionAutoTimeoutMs = DEFAULT_CONNECTION_AUTO_TIMEOUT_MS;

    @Configuration(TransportNames.codecName)
    protected String codec = DEFAULT_CODEC;

    // registry configs
    @Configuration(RegistryNames.evictionTimeoutMsName)
    protected long evictionTimeoutMs = DEFAULT_EVICTION_TIMEOUT;

    @Configuration(RegistryNames.evictionStrategyTypeName)
    protected String evictionStrategyType = EvictionStrategyProvider.StrategyType.PercentageDrop.name();

    @Configuration(RegistryNames.evictionStrategyValueName)
    protected String evictionStrategyValue = "20";


    // For property injection
    protected EurekaCommonConfig() {
    }

    protected EurekaCommonConfig(
            ResolverType resolverType,
            String[] serverList,
            String appName,
            String vipAddress,
            String readClusterVipAddress,
            LocalDataCenterInfo.DataCenterType dataCenterType,
            int dataCenterResolveIntervalSec,
            int httpPort,
            int shutDownPort,
            int webAdminPort,
            long heartbeatIntervalMs,
            long connectionAutoTimeoutMs,
            CodecType codec,
            long evictionTimeoutMs,
            StrategyType evictionStrategyType,
            String evictionStrategyValue
    ) {
        this.resolverType = resolverType == null ? this.resolverType : resolverType.name();
        this.serverList = serverList == null ? this.serverList : serverList;
        this.appName = appName == null ? this.appName : appName;
        this.vipAddress = vipAddress == null ? this.vipAddress : vipAddress;
        this.readClusterVipAddress = readClusterVipAddress == null ? this.readClusterVipAddress : readClusterVipAddress;
        this.dataCenterType = dataCenterType == null ? this.dataCenterType : dataCenterType.name();
        this.dataCenterResolveIntervalSec = dataCenterResolveIntervalSec;
        this.httpPort = httpPort;
        this.shutDownPort = shutDownPort;
        this.webAdminPort = webAdminPort;
        this.heartbeatIntervalMs = heartbeatIntervalMs;
        this.connectionAutoTimeoutMs = connectionAutoTimeoutMs;
        this.codec = codec == null ? this.codec : codec.name();
        this.evictionTimeoutMs = evictionTimeoutMs;
        this.evictionStrategyType = evictionStrategyType == null ? this.evictionStrategyType : evictionStrategyType.name();
        this.evictionStrategyValue = evictionStrategyValue == null ? this.evictionStrategyValue : evictionStrategyValue;
    }

    public String[] getServerList() {
        return serverList;
    }

    public ResolverType getServerResolverType() {
        ResolverType result;
        try {
            result = ResolverType.valueOfIgnoreCase(resolverType);
        } catch (Exception e) {
            return ResolverType.Fixed;
        }
        return result;
    }

    public String getAppName() {
        return appName;
    }

    public String getVipAddress() {
        return vipAddress;
    }

    public String getReadClusterVipAddress() {
        return readClusterVipAddress;
    }

    public LocalDataCenterInfo.DataCenterType getMyDataCenterType() {
        LocalDataCenterInfo.DataCenterType result;
        try {
            result = LocalDataCenterInfo.DataCenterType.valueOf(dataCenterType);
        } catch (Exception e) {
            result = LocalDataCenterInfo.DataCenterType.Basic;
        }
        return result;
    }

    public long getDataCenterResolveIntervalSec() {
        return dataCenterResolveIntervalSec;
    }

    public int getHttpPort() {
        return httpPort;
    }

    public int getWebAdminPort() {
        return webAdminPort;
    }

    public int getShutDownPort() {
        return shutDownPort;
    }

    @Override
    public long getHeartbeatIntervalMs() {
        return heartbeatIntervalMs;
    }

    @Override
    public long getConnectionAutoTimeoutMs() {
        return connectionAutoTimeoutMs;
    }

    @Override
    public CodecType getCodec() {
        CodecType result;
        try {
            result = CodecType.valueOf(codec);
        } catch (Exception e) {
            return CodecType.Avro;
        }
        return result;
    }

    @Override
    public long getEvictionTimeoutMs() {
        return evictionTimeoutMs;
    }

    @Override
    public EvictionStrategyProvider.StrategyType getEvictionStrategyType() {
        EvictionStrategyProvider.StrategyType type;
        try {
            type = EvictionStrategyProvider.StrategyType.valueOf(evictionStrategyType);
        } catch (Exception e) {
            type = EvictionStrategyProvider.StrategyType.PercentageDrop;
        }

        return type;
    }

    @Override
    public String getEvictionStrategyValue() {
        return evictionStrategyValue;
    }


    // builder
    public abstract static class EurekaCommonConfigBuilder<C extends EurekaCommonConfig, B extends EurekaCommonConfigBuilder<C, B>> {
        protected ResolverType resolverType;
        protected String[] serverList;
        protected String appName;
        protected String vipAddress;
        protected String readClusterVipAddress;
        protected LocalDataCenterInfo.DataCenterType dataCenterType;
        protected int dataCenterResolveIntervalSec = DEFAULT_DATACENTER_RESOLVE_INTERVAL_SEC;
        protected int httpPort = DEFAULT_HTTP_PORT;
        protected int shutDownPort = DEFAULT_SHUTDOWN_PORT;
        protected int webAdminPort = DEFAULT_ADMIN_PORT;

        protected long heartbeatIntervalMs = DEFAULT_HEARTBEAT_INTERVAL_MS;
        protected long connectionAutoTimeoutMs = DEFAULT_CONNECTION_AUTO_TIMEOUT_MS;
        protected CodecType codec = CodecType.Avro;

        protected long evictionTimeoutMs = DEFAULT_EVICTION_TIMEOUT;
        protected EvictionStrategyProvider.StrategyType evictionStrategyType;
        protected String evictionStrategyValue;

        public B withResolverType(ResolverType resolverType) {
            this.resolverType = resolverType;
            return self();
        }

        public B withResolverType(String resolverTypeStr) {
            this.resolverType = ResolverType.valueOfIgnoreCase(resolverTypeStr);
            return self();
        }

        public B withServerList(String[] serverList) {
            this.serverList = serverList;
            return self();
        }

        public B withAppName(String appName) {
            this.appName = appName;
            return self();
        }

        public B withVipAddress(String vipAddress) {
            this.vipAddress = vipAddress;
            return self();
        }

        public B withReadClusterVipAddress(String readClusterVipAddress) {
            this.readClusterVipAddress = readClusterVipAddress;
            return self();
        }

        public B withDataCenterType(LocalDataCenterInfo.DataCenterType dataCenterType) {
            this.dataCenterType = dataCenterType;
            return self();
        }

        public B withDataCenterResolveIntervalSec(int dataCenterResolveIntervalSec) {
            this.dataCenterResolveIntervalSec = dataCenterResolveIntervalSec;
            return self();
        }

        public B withHttpPort(int httpPort) {
            this.httpPort = httpPort;
            return self();
        }

        public B withShutDownPort(int shutDownPort) {
            this.shutDownPort = shutDownPort;
            return self();
        }

        public B withWebAdminPort(int webAdminPort) {
            this.webAdminPort = webAdminPort;
            return self();
        }

        public B withHeartbeatIntervalMs(long heartbeatIntervalMs) {
            this.heartbeatIntervalMs = heartbeatIntervalMs;
            return self();
        }

        public B withConnectionAutoTimeoutMs(long connectionAutoTimeoutMs) {
            this.connectionAutoTimeoutMs = connectionAutoTimeoutMs;
            return self();
        }

        public B withCodec(CodecType codec) {
            this.codec = codec;
            return self();
        }

        public B withEvictionTimeout(long evictionTimeoutMs) {
            this.evictionTimeoutMs = evictionTimeoutMs;
            return self();
        }

        public B withEvictionStrategyType(EvictionStrategyProvider.StrategyType strategyType) {
            this.evictionStrategyType = strategyType;
            return self();
        }

        public B withEvictionStrategyValue(String strategyValue) {
            this.evictionStrategyValue = strategyValue;
            return self();
        }

        public abstract C build();

        @SuppressWarnings("unchecked")
        protected B self() {
            return (B) this;
        }
    }


}
