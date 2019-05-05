package tw.NUTN.KuJiHiKi.api;


import com.google.protobuf.ServiceException;
import jdk.nashorn.internal.objects.annotations.Optimistic;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("Data_Create")
public class Default_option {
    @OPTIONS
    @Path("{stu_num}")
    public Response Data_Create_Func(@PathParam("stu_num") String stu_num_check) throws IOException, ServiceException {
        System.out.println("OPTION IN");
        return Response.ok().header("Access-Control-Allow-Origin", "*").header("Access-Control-Allow-Methods", "POST, GET, OPTIONS").header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Connection, User-Agent, Cookie, Authorization, *").build();
    }

}
