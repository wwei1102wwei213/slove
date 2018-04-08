package com.slove.dao.operate;

import com.slove.dao.conn.*;
import com.slove.util.*;

import java.sql.*;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

//import com.raymobile.wap.common.Tools;
/**
 * web 上的分页
 */
public class PaginationWeb {
	private String sql;
	private int intPageSize; // 每页行数
	private int intRowCount;
	private int intPageCount;
	private int intPage; // 页号
	private String Counter;

	public PaginationWeb() {

	}

	public PaginationWeb(String sqlcom, int rownum, int pagenum, String counter) {
		sql = sqlcom;
		intPageSize = rownum;
		intPage = pagenum;
		Counter = counter;
		// System.out.println(sqlcom);
	}

	public List selectRS(String sqlcom, int rownum, int pagenum, String counter) {
		this.sql = sqlcom;
		this.intPageSize = rownum;
		this.intPage = pagenum;
		this.Counter = counter;
		return selectRS();
	}

	public List selectRS() {
		List rsall = new ArrayList();
		Map rsTree;
		DBConnManager conn = null;
		Connection con = null;

		Statement st2 = null;
		ResultSet rs = null;
		Statement st = null;
		ResultSet rsc = null;
		try {
			conn = DBConnManager.getInstance();
			con = conn.getConnection("mssql");
			st = con.createStatement();
			rsc = st.executeQuery(Counter);
			while (rsc.next()) {
				intRowCount = rsc.getInt("allrow");
			}

			st2 = con.createStatement();
			sql += " limit " + (intPage - 1) * intPageSize + "," + intPageSize;
			rs = st2.executeQuery(sql);
			// System.out.println(sql);
			ResultSetMetaData rsmd = rs.getMetaData();
			int numberOfColumns = rsmd.getColumnCount();
			intPageCount = (intRowCount + intPageSize - 1) / intPageSize;
			while (rs.next()) {
				rsTree = new HashMap(numberOfColumns);
				for (int r = 1; r < numberOfColumns + 1; r++) {
					rsTree.put(rsmd.getColumnName(r), rs.getObject(r));
				}
				rsall.add(rsTree);
			}
		} catch (java.lang.Exception ex) {
			Tools.writeException(ex);
		} finally {
			try {
				if (rsc != null)
					rsc.close();
				if (st != null)
					st.close();
				if (rs != null)
					rs.close();
				if (st2 != null)
					st2.close();
				if (conn != null) {
					conn.releaseConnection("mssql", con);
				}
			} catch (Exception e) {
				Tools.writeException(e);
			}
		}
		return rsall;
	}
	public String ChangePageDown(HttpServletRequest request) {
		String urlchange = "";
		String pagename = request.getRequestURI().substring(
				request.getRequestURI().lastIndexOf("/") + 1);
		String url = getUrl(request);
		String dol = "";
		if (url != null && !url.equals(""))
			dol = "&";

		if (intRowCount > 0) {

			if (intRowCount < intPageSize * intPage + 1 && intPage > 1) {
				urlchange = "<input type='button'  onClick=\"location.href='" + pagename + "?type=down&pagenum=" + (intPage - 1)
						+ dol + url + "'\" value='上一页'>";
			} else if (intPage == 1 && intRowCount > intPageSize) {
				urlchange = "<input type='button' onClick=\"location.href='" + pagename + "?type=down&pagenum=" + (intPage + 1)
						+ dol + url +"'\" value='下一页'>";
			} else if (intPage > 1 && intRowCount > intPageSize * intPage) {
				urlchange = "<input type='button' onClick=\"location.href='" + pagename + "?type=down&pagenum=" + (intPage - 1)
						+ dol + url + "'\" value='上一页'>";
				urlchange = urlchange + "&nbsp;&nbsp;<input type='button' onClick=\"location.href='" + pagename
						+ "?type=down&pagenum=" + (intPage + 1) + dol + url + "'\" value='下一页'>";
			}
			urlchange = urlchange + "&nbsp;&nbsp;<input type='button' value='  "+this.intPage+"/"+this.getPageCount()+"  '>";
		}
		return urlchange;
	}

	
	public String ChangePageRead(HttpServletRequest request) {
		String urlchange = "";
		String pagename = request.getRequestURI().substring(
				request.getRequestURI().lastIndexOf("/") + 1);
		String url = getUrl(request);
		String dol = "";
		if (url != null && !url.equals(""))
			dol = "&";

		if (intRowCount > 0) {

			if (intRowCount < intPageSize * intPage + 1 && intPage > 1) {
				urlchange = "<input type='button'  onClick=\"location.href='" + pagename + "?type=read&pagenum=" + (intPage - 1)
						+ dol + url + "'\" value='上一页'>";
			} else if (intPage == 1 && intRowCount > intPageSize) {
				urlchange = "<input type='button' onClick=\"location.href='" + pagename + "?type=read&pagenum=" + (intPage + 1)
						+ dol + url +"'\" value='下一页'>";
			} else if (intPage > 1 && intRowCount > intPageSize * intPage) {
				urlchange = "<input type='button' onClick=\"location.href='" + pagename + "?type=read&pagenum=" + (intPage - 1)
						+ dol + url + "'\" value='上一页'>";
				urlchange = urlchange + "&nbsp;&nbsp;<input type='button' onClick=\"location.href='" + pagename
						+ "?type=read&pagenum=" + (intPage + 1) + dol + url + "'\" value='下一页'>";
			}
			urlchange = urlchange + "&nbsp;&nbsp;<input type='button' value='  "+this.intPage+"/"+this.getPageCount()+"  '>";
		}
		return urlchange;
	}

	public String ChangePageButton(HttpServletRequest request) {
		String urlchange = "";
		String pagename = request.getRequestURI().substring(
				request.getRequestURI().lastIndexOf("/") + 1);
		String url = getUrl(request);
		String dol = "";
		if (url != null && !url.equals(""))
			dol = "&";

		if (intRowCount > 0) {

			if (intRowCount < intPageSize * intPage + 1 && intPage > 1) {
				urlchange = "<input type='button'  onClick=\"location.href='" + pagename + "?pagenum=" + (intPage - 1)
						+ dol + url + "'\" value='上一页'>";
			} else if (intPage == 1 && intRowCount > intPageSize) {
				urlchange = "<input type='button' onClick=\"location.href='" + pagename + "?pagenum=" + (intPage + 1)
						+ dol + url +"'\" value='下一页'>";
			} else if (intPage > 1 && intRowCount > intPageSize * intPage) {
				urlchange = "<input type='button' onClick=\"location.href='" + pagename + "?pagenum=" + (intPage - 1)
						+ dol + url + "'\" value='上一页'>";
				urlchange = urlchange + "&nbsp;&nbsp;<input type='button' onClick=\"location.href='" + pagename
						+ "?pagenum=" + (intPage + 1) + dol + url + "'\" value='下一页'>";
			}
			urlchange = urlchange + "&nbsp;&nbsp;<input type='button' value='  "+this.intPage+"/"+this.getPageCount()+"  '>";
		}
		return urlchange;
	}

	public String ChangePage(HttpServletRequest request) {
		String urlchange = null;
		String pagename = request.getRequestURI().substring(
				request.getRequestURI().lastIndexOf("/") + 1);
		String url = getUrl(request);
		String dol = "";
		if (url != null && !url.equals(""))
			dol = "&";

		if (intRowCount > 0) {
			/*
			 * if ( intRowCount < intPageSize* intPage+1 && intPage > 1 ) {
			 * urlchange
			 * ="<a href="+pagename+"?pagenum="+(intPage-1)+dol+url+">上一页</a>";
			 * } else if (intPage == 1 && intRowCount > intPageSize) {
			 * urlchange=
			 * "<a href="+pagename+"?pagenum="+(intPage+1)+dol+url+">下一页</a>"; }
			 * else if (intPage> 1 && intRowCount > intPageSize*intPage) {
			 * urlchange
			 * ="<a href="+pagename+"?pagenum="+(intPage-1)+dol+url+">上一页</a>";
			 * urlchange
			 * =urlchange+"&nbsp;&nbsp;<a href="+pagename+"?pagenum="+(intPage
			 * +1)+dol+url+">下一页</a>"; }
			 */

			if (intRowCount < intPageSize * intPage + 1 && intPage > 1) {
				urlchange = "<a href=" + pagename + "?pagenum=" + (intPage - 1)
						+ ">&#x4E0A;&#x4E00;&#x9875;</a>";
			} else if (intPage == 1 && intRowCount > intPageSize) {
				urlchange = "<a href=" + pagename + "?pagenum=" + (intPage + 1)
						+ ">&#x4E0B;&#x4E00;&#x9875;</a>";
			} else if (intPage > 1 && intRowCount > intPageSize * intPage) {
				urlchange = "<a href='" + pagename + "?pagenum="
						+ (intPage + 1) + "'>&#x4E0B;&#x4E00;&#x9875;</a>";
				urlchange = urlchange + "&nbsp;&nbsp;<a href='" + pagename
						+ "?pagenum=" + (intPage - 1)
						+ "'>&#x4E0A;&#x4E00;&#x9875;</a>";
			}

		}
		return urlchange;
	}

	public int getTotal() {
		return intRowCount;
	}

	public int getPageCount() {
		return intPageCount;
	}

	public String gotoPage(HttpServletRequest request) {
		String url = getUrl(request);
		String javascript = "<script>function checksearch()\n{if(find.pagenum.value==\"\" || find.pagenum.value==\"0\"){alert('请输正确入页数！');find.pagenum.focus();return false;}\nif(isNaN(find.pagenum.value)){alert('请输入数字！');find.pagenum.focus();return false;}}</script>";
		String form = "<table  border='0' cellpadding='0' cellspacing='0'>\n"
				+ javascript;
		form += "<form name='find'  action='' onSubmit='return checksearch()'>\n<tr><td width='40' align='center'>\n<input name='pagenum' type='text' size='6' class='gotext'>\n";
		if (url != null) {
			if (url.indexOf("&") > 0) {
				String[] param = new String[(url.split("&")).length];
				param = url.split("&");
				for (int i = 0; i < param.length; i++) {
					form += "<input type='hidden' name='"
							+ param[i].substring(0, param[i].indexOf("="))
							+ "' value='"
							+ param[i].substring(param[i].indexOf("=") + 1)
							+ "'>\n";
				}
			} else {
				if (url.indexOf("=") > 0) {
					form += "<input type='hidden' name='"
							+ url.substring(0, url.indexOf("=")) + "' value='"
							+ url.substring(url.indexOf("=") + 1) + "'>\n";
				}
			}
		}
		form += "</td><td width='25' align='center'><input type='submit' name='Submit' value='GO' class='gobtn'>\n</td>\n</tr>\n</form>\n</table>";
		return form;
	}

	private String getUrl(HttpServletRequest request) {
		String url = "";
		Enumeration param = request.getParameterNames();

		while (param.hasMoreElements()) {
			String pname = param.nextElement().toString();
			if (!pname.equalsIgnoreCase("pagenum")
					&& !pname.equalsIgnoreCase("submit"))
				url += pname + "=" + request.getParameter(pname) + "&";

		}
		if (url.endsWith("&")) {
			url = url.substring(0, url.lastIndexOf("&"));
		}
		return url;
	}

	public String intercept(String str, int num, String last) {
		if (str.length() <= num)
			return str;
		else
			return str.substring(0, num) + last;
	}

	public String subURL(String url, String str) {
		String link = "";
		try {

			String point = "、";
			String[] name = new String[(str.split(point)).length];
			name = str.split(point);

			for (int i = 0; i < name.length; i++) {
				if (i == name.length - 1) {
					point = "";
				}
				link += "<a href=" + url + name[i] + " target=_blank>"
						+ name[i] + "</a>" + point;

			}
		} catch (Exception e) {
			Tools.writeException(e);
		}
		return link;
	}

}
