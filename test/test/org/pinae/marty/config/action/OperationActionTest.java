package test.org.pinae.marty.config.action;

import java.util.Map;

import org.pinae.ndb.action.OperationAction;

public class OperationActionTest implements OperationAction{

	@Override
	public Object handle(Object item) {
		if (item instanceof Map){
			Map map = (Map)item;
			map.put("flag", "pinae");
			return map;
		}
		return item;
	}

}
