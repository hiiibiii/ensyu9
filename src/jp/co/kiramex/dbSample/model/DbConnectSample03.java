package jp.co.kiramex.dbSample.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbConnectSample03 {

	public static void main(String[] args) {
		// 3. データベース接続と結果取得のための変数宣言
		Connection con = null; //DBの接続
		Statement stmt = null; //DBとやり取りする窓口
		ResultSet rs = null; //SQLの戻り値の型宣言
		try {
	        // 1. ドライバのクラスをJava上で読み込む
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
//			rs = stmt.executeQuery(sql); //検索したいときのクエリ

			//Lesson9 Chapter6「動的なSQLの作成」追記
			System.out.println("検索キーワードを入力してください > ");
			String input = keyIn();

			String sql = "select * from country where Name = '" + input + "'";
			rs = stmt.executeQuery(sql);


			// 7. 結果を表示する
			while( rs.next()) {
				//Name列の値を取得
				String name = rs.getString("Name");
				//Lesson9 Chapter 4.2「検索する処理の解説」
				int population = rs.getInt("Population");

				System.out.println(name);
				System.out.println(population); //9-4.2 追記
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
	/*
     * キーボードから入力された値をStringで返す 引数：なし 戻り値：入力された文字列    // ← 追記
     */
	private static String keyIn() {
		String line = null;
		try {
			BufferedReader key = new BufferedReader(new InputStreamReader(System.in));
			line = key.readLine();
		} catch (IOException e) {

		}
		return line;
	}
}
