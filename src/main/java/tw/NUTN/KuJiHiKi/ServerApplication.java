package tw.NUTN.KuJiHiKi;

import tw.NUTN.KuJiHiKi.api.Data_Create;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

public class ServerApplication extends Application {
	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> restServiceSet = new HashSet<Class<?>>();

		restServiceSet.add(Data_Create.class);

		return restServiceSet;
	}
}