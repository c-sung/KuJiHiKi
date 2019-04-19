package tw.NUTN.KuJiHiKi.api;

import com.google.gson.Gson;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


@Path("weatherByMap")
public class Weather_Look_Up {

    private static final Gson GSON = new Gson();
    private static volatile Map<String, String> place_convert = new HashMap<>();

    @Produces("application/json")
    @Path("{place}")
    @GET
    public Response lookUp(@PathParam("place") String placeToLookFor) throws IOException {

        System.out.println("OK");
        setUpMap();
        Response_Answer response_answer = new Response_Answer();

        if(place_convert.containsKey(placeToLookFor)) {
            placeToLookFor = place_convert.get(placeToLookFor);
        } else {
            response_answer.setDescription("bad999");
            response_answer.setMainDescription("bad999");
            response_answer.setHumid("bad999");
            response_answer.setTemp("bad999");
            return Response.ok().entity(GSON.toJson(response_answer)).build();
        }

        String rsp_msg;
        String mainDescription, description, temp, humid;

        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + placeToLookFor +"&appid=ed08afe632ddd86386c704a976a14a54";

        URL url_connection = new URL(url);
        HttpURLConnection connection = (HttpURLConnection)url_connection.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader rspReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        rsp_msg = rspReader.readLine();

        mainDescription = rsp_msg.substring(rsp_msg.indexOf("\"main\":\""), rsp_msg.indexOf(",", rsp_msg.indexOf("\"main\":\""))).replace("\"main\":\"", "").replace("\"", "");
        description = rsp_msg.substring(rsp_msg.indexOf("\"description\":\""), rsp_msg.indexOf(",", rsp_msg.indexOf("\"description\":\""))).replace("\"description\":\"", "").replace("\"", "");
        temp = rsp_msg.substring(rsp_msg.indexOf("\"temp\":"), rsp_msg.indexOf(",", rsp_msg.indexOf("\"temp\":"))).replace("\"temp\":", "");
        humid = rsp_msg.substring(rsp_msg.indexOf("\"humidity\":"), rsp_msg.indexOf(",", rsp_msg.indexOf("\"humidity\":"))).replace("\"humidity\":", "");

        response_answer.setDescription(description);
        response_answer.setMainDescription(mainDescription);
        response_answer.setHumid(humid);
        response_answer.setTemp(String.valueOf(Float.parseFloat(temp) - 273));

        return Response.ok().entity(GSON.toJson(response_answer)).build();
    }

    private static void setUpMap() {
        place_convert.put("台北", "Taipei");
        place_convert.put("宜蘭", "Yilan");
        place_convert.put("桃園", "Taoyuan");
        place_convert.put("新竹", "Hsinchu");
        place_convert.put("苗栗", "Miaoli");
        place_convert.put("台中", "Taichung");
        place_convert.put("彰化", "Changhua");
        place_convert.put("南投", "Nantou");
        place_convert.put("基隆", "Keelung");
        place_convert.put("雲林", "Yunlin");
        place_convert.put("嘉義", "Chiayi");
        place_convert.put("台南", "Tainan");
        place_convert.put("高雄", "Kaohsiung");
        place_convert.put("屏東", "Pingtung");
        place_convert.put("台東", "Taitung");
        place_convert.put("花蓮", "Hualien");
        place_convert.put("澎湖", "Penghu");
        place_convert.put("金門", "Kinmen");
        place_convert.put("連江", "Lienchiang");
        place_convert.put("臺北", "Taipei");
        place_convert.put("臺中", "Taichung");
        place_convert.put("臺南", "Tainan");
        place_convert.put("臺東", "Taitung");

    }

}




