package tw.NUTN.KuJiHiKi;

import tw.NUTN.KuJiHiKi.api.*;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

public class ServerApplication extends Application {
	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> restServiceSet = new HashSet<Class<?>>();

		restServiceSet.add(Data_Create.class);
		restServiceSet.add(Get_Data.class);
		restServiceSet.add(Delete_Data.class);
		restServiceSet.add(Default_option.class);
		restServiceSet.add(Update_Data.class);

		return restServiceSet;
	}
}