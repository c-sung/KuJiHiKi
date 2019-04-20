package tw.NUTN.KuJiHiKi.api;

import com.google.gson.Gson;
import com.google.protobuf.ServiceException;
import tw.NUTN.KuJiHiKi.obj.Data_Create_Response;
import tw.NUTN.KuJiHiKi.obj.Member;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

@Path("Data_Create")
public class Data_Create {
    private static final Gson GSON = new Gson();
    private static final int DATA_ALREADY_EXIST = 919;
    private static final int DATA_NULL = 915;
    private static final int DATA_ADD_SUCCESS = 210;
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
    @POST
    public Response Data_Create_Func(@PathParam("stu_num") String stu_num_check, String body) throws IOException, ServiceException {
        Data_Create_Response data_create_response = new Data_Create_Response();
        hbaseConfig.set("hbase.zookeeper.quorum", "localhost");
        table = new HTable(hbaseConfig, "kuji");
        Member member = new Member();

        member = GSON.fromJson(body, member.getClass());

        if(data_exist_in_hbase(stu_num_check)) {
            data_create_response.setRsp_code(DATA_ALREADY_EXIST);
            data_create_response.setRsp_msg("data already exist");
            return Response.ok().entity(GSON.toJson(data_create_response)).build();
        }

        if(!member.check_data()) {
            data_create_response.setRsp_code(DATA_NULL);
            data_create_response.setRsp_msg("Data is null");
            return Response.ok().entity(GSON.toJson(data_create_response)).build();
        }

        data_put(member);
        data_create_response.setRsp_code(DATA_ADD_SUCCESS);
        data_create_response.setRsp_msg("success");

        System.out.println(GSON.toJson(member));
        return Response.ok().entity(GSON.toJson(data_create_response)).build();
    }

    private static boolean data_exist_in_hbase(String stuNum) throws IOException {
        boolean exist = false;
        Result result = new Result();
        Get get = new Get(Bytes.toBytes(stuNum));
        get.addColumn(CF, STU_NUM);
        result = table.get(get);

        if(result.getValue(CF, STU_NUM) != null) {
            exist = true;
        }

        return exist;
    }

    private static void data_put(Member member) throws IOException {
        Put put = new Put(Bytes.toBytes(member.getStu_num()));
        put.addColumn(CF, W_NAME, Bytes.toBytes(member.getWriter_name()));
        put.addColumn(CF, W_STU_NUM, Bytes.toBytes(member.getWriter_stuNum()));
        put.addColumn(CF, NAME, Bytes.toBytes(member.getName()));
        put.addColumn(CF, STU_NUM, Bytes.toBytes(member.getStu_num()));
        put.addColumn(CF, CONTENT, Bytes.toBytes(member.getContent()));
        put.addColumn(CF, SECURITY_CODE, Bytes.toBytes(member.getSecurity_code()));
        put.addColumn(CF, PHONE_NUMBER, Bytes.toBytes(member.getPhone_number()));
        table.put(put);
    }
}
