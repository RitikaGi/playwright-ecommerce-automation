	package utils;
	
	public class TestData {
		
        public static final String INVALID_PASSWORD = "invalid_password";
		
		public enum Products{
			
			BACKPACK("Sauce Labs Backpack","29.99"),
			BIKE_LIGHT("Sauce Labs Bike Light","9.99"),
			BOLT_TSHIRT("Sauce Labs Bolt T-Shirt","15.99"),
			FLEECE_JACKET("Sauce Labs Fleece Jacket","49.99"),
			ONESIE("Sauce Labs Onesie","7.99"),
			RED_JACKET("Test.allTheThings() T-Shirt (Red)","15.99");
			
			private final String name;
			private final String price;
			
			Products(String name, String price){
				this.name=name;
				this.price=price;
			}
			
			public String getName() {
				return name;
			}
			
			public String getPrice() {
				return price;
			}
			
		}
		
	public enum Users{
			STANDARD_USER("standard_user"),
			LOCKED_OUT_USER("locked_out_user"),
			PROBLEM_USER("problem_user"),
			PERFORMANCE_GLITCH_USER("performance_glitch_user"),
		    INVALID_USER("invalidUser");
		
		
		private final String username;
		private static final String commonPassword="secret_sauce";
		
		Users(String username){
			this.username=username;
		
		}
		
		public String getUsername() {
			return username;
		}
		
		public String getPassword() {
			return commonPassword;
		}
	
	}
	}
