import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class CalculateOrdersTest {

	OrderLine ol=null;
	calculator c=new calculator();

	//Test case to check whether the program is giving proper output or not
	@Test
	public void testMain() throws Exception {
		CalculateOrders.main(new String[0]);
	}
	//Test case to find whether the OrderLine class is throwing exception or not in case of null order details
	@Test(expected=Exception.class)
	public void testOrderLineNull() throws Exception  {
		ol=new OrderLine(null, 0);
	}
	//Test case to check the calculate method
	@Test
	public void testCalculate() throws Exception
	{
		Order o=new Order();
		Map<String, Order> m=new HashMap<String, Order>();
		o.add(new OrderLine(new Item("BookStore", (float) 15.98), 2));
		m.put("Key1", o);
		c.calculate(m);
	}
	
	//Testcase to check the getters 
	@Test
	public void testOrder() throws Exception
	{
		Order o=new Order();
		o.add(new OrderLine(new Item("Library", (float) 15.90), 3));
		assertEquals(3, o.get(0).getQuantity());
		assertEquals("Library", o.get(0).getItem().getDescription());
	}

}
