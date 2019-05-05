package tw.NUTN.KuJiHiKi.api;

import com.google.gson.Gson;
import com.google.protobuf.ServiceException;
import tw.NUTN.KuJiHiKi.obj.Data_to_Delete;
import tw.NUTN.KuJiHiKi.obj.Delete_Data_Response;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

@Path("Delete_Data")
public class Delete_Data {
    private static final Gson GSON = new Gson();
    private static final int DATA_NOT_EXIST = 939;
    private static final int SECURITY_CODE_INCORRECT = 935;
    private static final int OK = 230;
    private static final byte[] STU_NUM = Bytes.toBytes("stu_num");
    private static final byte[] SECURITY_CODE = Bytes.toBytes("security_code");
    private static final byte[] CF = Bytes.toBytes("cf");
    private static final Configuration hbaseConfig = HBaseConfiguration.create();
    private static volatile HTable table = null;

    @Produces("application/json")
    @Path("{stu_num}/{sc}")
    @DELETE
    public Response Delete_Data_func(@PathParam("stu_num") String stu_num_check, @PathParam("sc") String sc) throws IOException, ServiceException {
        Data_to_Delete data_to_delete = new Data_to_Delete();
        Delete_Data_Response response = new Delete_Data_Response();
        hbaseConfig.set("hbase.zookeeper.quorum", "localhost");
        table = new HTable(hbaseConfig, "kuji");

        data_to_delete = get_data(stu_num_check);

        if(data_to_delete.isEmpty()) {
            response.setRsp_code(DATA_NOT_EXIST);
            response.setRsp_msg("Data doesn't exist");
        } else {
            if(!data_to_delete.getSecurity_code().equals(sc)) {
                response.setRsp_code(SECURITY_CODE_INCORRECT);
                response.setRsp_msg("security code incorrect");
            } else {
                Delete delete = new Delete(Bytes.toBytes(stu_num_check));
                table.delete(delete);
                response.setRsp_code(OK);
                response.setRsp_msg("success");
            }
        }

        System.out.println("Someone tried to delete a data");
        return Response.ok().entity(GSON.toJson(response)).header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Connection, User-Agent, Cookie, Authorization, *").header("Access-Control-Allow-Methods", "POST, GET, OPTIONS").header("Access-Control-Allow-Origin", "*").build();
    }

    private static Data_to_Delete get_data(String stu_num_to_del) throws IOException {
        Data_to_Delete data = new Data_to_Delete();
        Result result = new Result();
        Get get = new Get(Bytes.toBytes(stu_num_to_del));
        get.addColumn(CF, STU_NUM);
        get.addColumn(CF, SECURITY_CODE);
        result = table.get(get);

        if(Bytes.toString(result.getValue(CF, STU_NUM)) == null) {
            data.setSecurity_code(String.valueOf(DATA_NOT_EXIST));
            data.setStu_num(String.valueOf(DATA_NOT_EXIST));
        } else {
            data.setSecurity_code(Bytes.toString(result.getValue(CF, SECURITY_CODE)));
            data.setStu_num(Bytes.toString(result.getValue(CF, STU_NUM)));
        }

        return data;
    }
}
