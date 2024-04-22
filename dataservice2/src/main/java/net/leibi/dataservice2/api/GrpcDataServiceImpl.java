package net.leibi.dataservice2.api;

import com.google.protobuf.RpcCallback;
import com.google.protobuf.RpcController;
import net.devh.boot.grpc.server.service.GrpcService;
import net.leibi.dataservice2.grpc.DataRequest;
import net.leibi.dataservice2.grpc.DataResponse;
import net.leibi.dataservice2.grpc.GrpcDataService;

@GrpcService
public class GrpcDataServiceImpl extends GrpcDataService {
    @Override
    public void getData(RpcController controller, DataRequest request, RpcCallback<DataResponse> done) {
        done.run(DataResponse.newBuilder().build());
    }
}
