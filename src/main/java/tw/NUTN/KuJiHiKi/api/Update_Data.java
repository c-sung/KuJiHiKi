package tw.NUTN.KuJiHiKi.api;

import com.google.gson.Gson;
import com.google.protobuf.ServiceException;
import tw.NUTN.KuJiHiKi.obj.Data_Update_Response;
import tw.NUTN.KuJiHiKi.obj.Data_to_Update;
import tw.NUTN.KuJiHiKi.obj.Member;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

@Path("Update_Data")
public class Update_Data {
    private static final Gson GSON = new Gson();
    private static final int DATA_UPDATE_SUCCESS = 240;
    private static final int DATA_NOT_EXIST = 939;
    private static final int SECURITY_CODE_INCORRECT = 935;
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
    @Path("{stu_num}/{sc}")
    @PUT
    public Response Update_Data_func(@PathParam("stu_num") String stu_num_check, @PathParam("sc") String sc,  String body) throws IOException, ServiceException {
        Data_to_Update data_to_update = new Data_to_Update();
        Data_Update_Response data_update_response = new Data_Update_Response();
        hbaseConfig.set("hbase.zookeeper.quorum", "localhost");
        table = new HTable(hbaseConfig, "kuji");

        data_to_update = get_data(stu_num_check);

        if(data_to_update.isEmpty()) {
            data_update_response.setRsp_code(DATA_NOT_EXIST);
            data_update_response.setRsp_msg("Data doesn't exist");
            return Response.ok().entity(GSON.toJson(data_update_response)).build();
        } else {
            if(!data_to_update.getSecurity_code().equals(sc)) {
                data_update_response.setRsp_code(SECURITY_CODE_INCORRECT);
                data_update_response.setRsp_msg("security code incorrect");
                return Response.ok().entity(GSON.toJson(data_update_response)).build();
            }
        }

        Member member = new Member();

        member = GSON.fromJson(body, member.getClass());

        data_put(member);
        data_update_response.setRsp_code(DATA_UPDATE_SUCCESS);
        data_update_response.setRsp_msg("success");

        System.out.println(GSON.toJson(member));
        return Response.ok().entity(GSON.toJson(data_update_response)).build();
    }

    private static Data_to_Update get_data(String stu_num_to_upd) throws IOException {
        Data_to_Update data = new Data_to_Update();
        Result result = new Result();
        Get get = new Get(Bytes.toBytes(stu_num_to_upd));
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

    private static void data_put(Member member) throws IOException {
        Put put = new Put(Bytes.toBytes(member.getStu_num()));

        if(member.getName() != null){
            put.addColumn(CF, NAME, Bytes.toBytes(member.getName()));
        }

        if(member.getStu_num() != null){
            put.addColumn(CF, STU_NUM, Bytes.toBytes(member.getStu_num()));
        }

        if(member.getContent() != null){
            put.addColumn(CF, CONTENT, Bytes.toBytes(member.getContent()));
        }

        if(member.getPhone_number() != null){
            put.addColumn(CF, PHONE_NUMBER, Bytes.toBytes(member.getPhone_number()));
        }

        table.put(put);
    }
}
