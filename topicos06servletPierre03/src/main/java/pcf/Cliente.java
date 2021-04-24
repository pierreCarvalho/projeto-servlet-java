package pcf;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Cliente
 */
@WebServlet("/Cliente")
public class Cliente extends HttpServlet {
	public Connection conn;
    public Statement stmt;
    public ResultSet rs;
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Cliente() {
        super();
        
        
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		//PrintWriter printWriter = response.getWriter();
		conectar();
        
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		
		out.println("<html>\r\n");
		out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
		out.println("<body>");
		
		try {
			out.println("<h2>Lista de clientes cadastrados</h2>");
            rs = stmt.executeQuery("SELECT * FROM cliente");
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
		
		if (rs != null) {
            try {
                while (rs.next()) {
                	out.println("</strong>ID: </strong>" + rs.getString("id") + "<br>");
                    out.println("</strong>Nome: </strong>" + rs.getString("nome") + "<br>");
                    out.println("</strong>Telefone: </strong>" + rs.getString("telefone") + "<br>");
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
		out.println("</body>\r\n");
		out.println("</html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String nome = request.getParameter("nome");
		String telefone = request.getParameter("telefone");
		//String cidade = request.getParameter("cidade");
		//criar objeto cliente a partir do model cliente
		//chamar cadastro
		conectar();
		
		// INSERIR A PESSOA
		
        
		System.out.println(nome);
		System.out.println(telefone);
		String query = "INSERT INTO Cliente (nome, telefone) "
                + "values ('"+nome+"', '"+telefone+"')";
		int status = executeUpdate(query);
		
		if (status==1) {
            System.out.println("Inserido.");
        } else {
            System.out.println("Erro ao inserir");
        }
		doGet(request, response);
	}

	private void conectar() {
    	try {
			String address = "localhost";
			String port = "3306";
			String dataBaseName = "banco_projeto";
			String user = "root";
			String password = "root";
			
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://"+ address + ":" + port +"/"+ dataBaseName + "?user=" + user + "&password=" + password + "&useTimezone=true&serverTimezone=UTC"); 
			stmt = conn.createStatement();
			
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
    public int executeUpdate(String query) {     
        int status = 0;
        try {
            stmt = conn.createStatement();           
            status = stmt.executeUpdate(query);
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return status;
    }
    

    public ResultSet executeQuery(String query) {
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return rs;   
    }

}
