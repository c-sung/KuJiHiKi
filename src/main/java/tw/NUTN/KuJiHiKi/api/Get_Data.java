package tw.NUTN.KuJiHiKi.api;

import com.google.gson.Gson;
import com.google.protobuf.ServiceException;
import tw.NUTN.KuJiHiKi.obj.Data_Create_Response;
import tw.NUTN.KuJiHiKi.obj.Data_Get_Response;
import tw.NUTN.KuJiHiKi.obj.Member;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

@Path("Get_Data")
public class Get_Data {
    private static final Gson GSON = new Gson();
    private static final int DATA_NOT_FOUND = 929;
    private static final int OK = 220;
    private static final byte[] W_NAME = Bytes.toBytes("writer_name");
    private static final byte[] W_STU_NUM = Bytes.toBytes("writer_stuNum");
    private static final byte[] NAME = Bytes.toBytes("name");
    private static final byte[] STU_NUM = Bytes.toBytes("stu_num");
    private static final byte[] CONTENT = Bytes.toBytes("content");
    private static final byte[] SECURITY_CODE = Bytes.toBytes("security_code");
    private static final byte[] PHONE_NUMBER = Bytes.toBytes("phone_number");
    private static final byte[] CF = Bytes.toBytes("cf");
    private static final Configuration hbaseConfig = HBaseConfiguration.create();
    private static volatile HTable table = null;

    @Produces("application/json")
    @Path("{stu_num}")
    @GET
    public Response Data_Get(@PathParam("stu_num") String stu_num_check) throws IOException, ServiceException {

        hbaseConfig.set("hbase.zookeeper.quorum", "localhost");
        table = new HTable(hbaseConfig, "kuji");

        Data_Get_Response response = get_Data(stu_num_check);

        return Response.ok().entity(GSON.toJson(response)).build();
    }

    private static Data_Get_Response get_Data(String stu_num) throws IOException {
        String name_str = "", content_str = "";
        Result result = new Result();
        Data_Get_Response response = new Data_Get_Response();
        Get get = new Get(Bytes.toBytes(stu_num));
        get.addColumn(CF, NAME);
        get.addColumn(CF, STU_NUM);
        get.addColumn(CF, CONTENT);
        get.addColumn(CF, PHONE_NUMBER);
        result = table.get(get);

        if(Bytes.toString(result.getValue(CF, STU_NUM)) == null) {
            response.setName_r(String.valueOf(DATA_NOT_FOUND));
            response.setContent_r(String.valueOf(DATA_NOT_FOUND));
            response.setPhone_num_r(String.valueOf(DATA_NOT_FOUND));
            response.setStu_num_r(String.valueOf(DATA_NOT_FOUND));
        } else {
            name_str = java.net.URLDecoder.decode(Bytes.toString(result.getValue(CF, NAME)), "UTF-8");
            content_str = java.net.URLDecoder.decode(Bytes.toString(result.getValue(CF, CONTENT)), "UTF-8");

            response.setName_r(name_str);
            response.setContent_r(content_str);
            response.setPhone_num_r(Bytes.toString(result.getValue(CF, PHONE_NUMBER)));
            response.setStu_num_r(Bytes.toString(result.getValue(CF, STU_NUM)));
        }
        return response;
    }
}