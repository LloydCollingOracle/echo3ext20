/* =================================================================
# This library is free software; you can redistribute it and/or
# modify it under the terms of the GNU Lesser General Public
# License as published by the Free Software Foundation; either
# version 2.1 of the License, or (at your option) any later version.
#
# This library is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
# Lesser General Public License for more details.
#
# You should have received a copy of the GNU Lesser General Public
# License along with this library; if not, write to the Free Software
# Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
#
# ================================================================= */
package org.sgodden.echo.ext20.json;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sdicons.json.mapper.JSONMapper;
@SuppressWarnings({"serial"})
public class JsonServlet 
    extends HttpServlet {
    
    private static final transient Log log = LogFactory.getLog(JsonServlet.class);
    
    private static List<Order> orders = generateOrders();
    
    public void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException {
        log.info("JsonServlet was called");
        
        try {
            
            int offset = Integer.parseInt(request.getParameter("offset"));
            int limit = Integer.parseInt(request.getParameter("limit"));
            
            Response output = new Response( 
                orders.subList(offset, offset + limit),
                orders.size()
            );
            response.getOutputStream().write(JSONMapper.toJSON(output).render(true).getBytes());
        }
        catch(Exception e) {
            throw new ServletException(e);
        }
    }
    
    private static List<Order> generateOrders() {
        List<Order> ret = new ArrayList<Order>();
        for (int i = 0; i < 1000; i++) {
            Order order = new Order("Order number" + i, "03/02/2008");
            ret.add(order);
        }
        
        return ret;
    }
    
    public static class Response {
        private List<?> records;
        private int totalCount;

        public Response(List<?> records, int totalCount) {
            this.records = records;
            this.totalCount = totalCount;
        }
        
        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }
        
        public List<?> getRecords(){
            return records;
        }
        
        public void setRecords(List<?> records) {
            this.records = records;
        }
    }
    
    public static class Order {
        private String orderNumber;
        private String orderDate;
        
        private Order(String orderNumber, String orderDate) {
            this.orderNumber = orderNumber;
            this.orderDate = orderDate;
        }

        public String getOrderNumber() {
            return orderNumber;
        }

        public void setOrderNumber(String orderNumber) {
            this.orderNumber = orderNumber;
        }

        public String getOrderDate() {
            return orderDate;
        }

        public void setOrderDate(String orderDate) {
            this.orderDate = orderDate;
        }
        
    }

}
