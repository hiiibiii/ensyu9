package jp.co.kiramex.dbSample.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbConnectSample02 {

	public static void main(String[] args) {
		// 3. データベース接続と結果取得のための変数宣言
		Connection con = null; //DBの接続
		Statement stmt = null; //DBとやり取りする窓口
		ResultSet rs = null; //SQLの戻り値の型宣言
		try {
	        // 1. ドライバのクラスをJava上で読み込む
			//例外処理発生可能性があるため、try-catch文が必要
			Class.forName("com.mysql.cj.jdbc.Driver");

			// 2. DBと接続する
			con = DriverManager.getConnection( //Connection不要
					"jdbc:mysql://localhost/world?useSSL=false&allowPublicKeyRetrieval=true",
					"root",
					"H556mysql@"
					);
        // 4. DBとやりとりする窓口（Statementオブジェクト）の作成
			stmt = con.createStatement();
        // 5, 6. Select文の実行と結果を格納／代入
//			String sql = "SELECT * FROM country LIMIT 50";
			String sql = "SELECT * FROM country where Code = 'ABW'"; //Lesson9 Chapter4.3追記
			rs = stmt.executeQuery(sql); //検索したいときのクエリ
        // 7. 結果を表示する
			// 7-1. 更新前の結果を表示する
			System.out.println("更新前==================="); //Lesson9 Chapter4.3追記
			if( rs.next()) { ////Lesson9 Chapter4.3追記
//			while( rs.next()) {
				//Name列の値を取得
				String name = rs.getString("Name");
				//Lesson9 Chapter 4.2「検索する処理の解説」
				int population = rs.getInt("Population");
				//Lesson9 Chapter4.3追記
				System.out.println(name + "\n" + population);
			}
			// 7-2. 更新処理を行なう
			System.out.println("更新処理実行=============");//Lesson9 Chapter4.3追記
			String updateSql = "update country set Population = 105000 where Code = 'ABW'";//Lesson9 Chapter4.3追記
			int count = stmt.executeUpdate(updateSql);//Lesson9 Chapter4.3追記
			System.out.println("更新行数" + count);//Lesson9 Chapter4.3追記

			// 7-3. 更新後の結果を表示する
			rs.close();//更新後の検索の為一度閉じる、Lesson9 Chapter4.3追記
			System.out.println("更新後=================");//Lesson9 Chapter4.3追記
			rs = stmt.executeQuery(sql);//Lesson9 Chapter4.3追記
			if( rs.next()){//Lesson9 Chapter4.3追記
//				System.out.println(name);
//				System.out.println(population); //9-4.2 追記
				 // Name列の値を取得
				String name = rs.getString("Name");//Lesson9 Chapter4.3追記
				// Population列の値を取得
				int population = rs.getInt("Population");//Lesson9 Chapter4.3追記
				 // 取得した値を表示
				System.out.println(name + "\n" + population);//Lesson9 Chapter4.3追記
			}
		} catch (ClassNotFoundException e) {
			System.err.println("JDBCドライバのロードに失敗しました。");
			e.printStackTrace();
		}
		catch (SQLException e) {
			System.err.println("データベースに異常が発生しました。");
			e.printStackTrace();
		}finally {
        // 8. 接続を閉じる
			//順番大事
			if( rs != null) {
				try {
					rs.close();
				}catch(SQLException e) {
					System.err.println("ResultSetを閉じるときにエラーが発生しました。");
					e.printStackTrace();
				}
			}
			if( stmt != null) {
				try {
					con.close();
				}catch(SQLException e) {
					System.err.println("Statementを閉じるときにエラーが発生しました。");
					e.printStackTrace();
				}
			}
			if(con != null) {
				try {
					con.close();
				}catch(SQLException e) {
					System.err.println("データベース切断時にエラーが発生しました。");
					e.printStackTrace();
				}
			}
		}
	}

}
