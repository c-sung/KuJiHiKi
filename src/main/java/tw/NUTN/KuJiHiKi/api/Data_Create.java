package tw.NUTN.KuJiHiKi.api;

import com.google.gson.Gson;
import com.google.protobuf.ServiceException;
import net.spy.memcached.compat.log.Log4JLogger;
import org.apache.hadoop.hbase.TableName;
import tw.NUTN.KuJiHiKi.obj.Member;

import javax.annotation.PostConstruct;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Path("Data_Create")
public class Data_Create {
    private static final Gson GSON = new Gson();
    private static volatile Member member = new Member();
    private static final byte[] W_NAME = Bytes.toBytes("writer_name");
    private static final byte[] W_STU_NUM = Bytes.toBytes("writer_stuNum");
    private static final byte[] NAME = Bytes.toBytes("name");
    private static final byte[] STU_NUM = Bytes.toBytes("stu_num");
    private static final byte[] CONTENT = Bytes.toBytes("content");
    private static final byte[] SECURITY_CODE = Bytes.toBytes("security_code");
    private static final byte[] PHONE_NUMBER = Bytes.toBytes("phone_number");
    private static final byte[] CF = Bytes.toBytes("cf");
    private static  Configuration hbaseConfig = HBaseConfiguration.create();

    @Produces("application/json")
    @Path("{stu_num}")
    @POST
    public Response Data_Create_Func(@PathParam("stu_num") String stu_num_check, String body) throws IOException, ServiceException {
        hbaseConfig.set("hbase.zookeeper.quorum", "SAIHS-NiNoMiYa-SERVER");
        hbaseConfig.set("hbase.zookeeper.property.clientPort", "2222");
        Connection connection = ConnectionFactory.createConnection(hbaseConfig);
        Admin admin = connection.getAdmin();

        System.out.println("建立連線成功");
//        hbaseConfig.set("hbase.zookeeper.quorum", "saihs");
//        hbaseConfig.set("hbase.zookeeper.property.clientPort", "2222");
//
//        HTable table = new HTable(hbaseConfig, "KuJi");
//
//        Put put = new Put(Bytes.toBytes("test"));
//        put.addColumn(CF, NAME, Bytes.toBytes("test"));
//
//        table.put(put);

        admin.listTables();
        System.out.println("OK");
        return Response.ok().entity("OKs").build();
    }
}
