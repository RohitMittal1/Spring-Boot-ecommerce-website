package com.eRohit.Ecom_proj;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import com.eRohit.Ecom_proj.model.Product;
import com.eRohit.Ecom_proj.service.ProductService;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
@EnableFeignClients
public class EcomProjApplication implements CommandLineRunner {

	private final ProductService productService;
	private final Scanner scanner = new Scanner(System.in);
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	public EcomProjApplication(ProductService productService) {
		this.productService = productService;
	}

	public static void main(String[] args) {
		SpringApplication.run(EcomProjApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("═══════════════════════════════════════════");
		System.out.println("    🛒 E-COMMERCE MANAGEMENT SYSTEM 🛒    ");
		System.out.println("═══════════════════════════════════════════");

		while (true) {
			showMainMenu();
			int choice = getIntInput("Enter your choice: ");

			switch (choice) {
				case 1 -> productMenu();
				case 2 -> {
					System.out.println("\n🎉 Thank you for using E-Commerce Management System!");
					System.out.println("👋 Goodbye!");
					System.exit(0);
				}
				default -> System.out.println("❌ Invalid choice! Please try again.");
			}
		}
	}

	private void showMainMenu() {
		System.out.println("\n╔═══════════════════════════════════════╗");
		System.out.println("║              MAIN MENU                ║");
		System.out.println("╠═══════════════════════════════════════╣");
		System.out.println("║  1. 📦 Product Management             ║");
		System.out.println("║  2. 🚪 Exit                           ║");
		System.out.println("╚═══════════════════════════════════════╝");
	}

	private void productMenu() {
		while (true) {
			showProductMenu();
			int choice = getIntInput("Enter your choice: ");

			switch (choice) {
				case 1 -> showAllProducts();
				case 2 -> showProductById();
				case 3 -> addNewProduct();
				case 4 -> updateProduct();
				case 5 -> deleteProduct();
				case 6 -> searchProducts();
				case 7 -> {
					System.out.println("🔙 Returning to main menu...\n");
					return;
				}
				default -> System.out.println("❌ Invalid choice! Please try again.");
			}
		}
	}

	private void showProductMenu() {
		System.out.println("\n╔═══════════════════════════════════════╗");
		System.out.println("║           PRODUCT MANAGEMENT          ║");
		System.out.println("╠═══════════════════════════════════════╣");
		System.out.println("║  1. 📋 Show All Products              ║");
		System.out.println("║  2. 🔍 Show Product by ID             ║");
		System.out.println("║  3. ➕ Add New Product                ║");
		System.out.println("║  4. ✏️  Update Product                ║");
		System.out.println("║  5. 🗑️  Delete Product                ║");
		System.out.println("║  6. 🔎 Search Products                ║");
		System.out.println("║  7. 🔙 Back to Main Menu              ║");
		System.out.println("╚═══════════════════════════════════════╝");
	}

	private void showAllProducts() {
		try {
			System.out.println("\n📦 ALL PRODUCTS:");
			System.out.println("─".repeat(80));

			List<Product> products = productService.getAllProducts();

			if (products.isEmpty()) {
				System.out.println("📪 No products available.");
				return;
			}

			System.out.printf("%-5s %-20s %-30s %-15s %-10s %-15s%n",
					"ID", "NAME", "DESCRIPTION", "BRAND", "PRICE", "CATEGORY");

			System.out.println("─".repeat(80));

			for (Product product : products) {
				System.out.printf("%-5d %-20s %-30s %-15s ₹%-9s %-15s%n",
						product.getId(),
						truncate(product.getName(), 20),
						truncate(product.getDescription(), 30),
						truncate(product.getBrand(), 15),
						product.getPrice(),
						truncate(product.getCategory(), 15));
			}

			System.out.println("─".repeat(80));
			System.out.println("📊 Total Products: " + products.size());

		} catch (Exception e) {
			System.out.println("❌ Error fetching products: " + e.getMessage());
		}
	}

	private void showProductById() {
		int id = getIntInput("🔢 Enter Product ID: ");

		try {
			Product product = productService.getProductById(id);

			if (product != null) {
				displayProductDetails(product);
			} else {
				System.out.println("❌ Product not found with ID: " + id);
			}

		} catch (Exception e) {
			System.out.println("❌ Error fetching product: " + e.getMessage());
		}
	}

	private void displayProductDetails(Product product) {
		System.out.println("\n╔═══════════════ PRODUCT DETAILS ═══════════════╗");
		System.out.println("║ ID          : " + product.getId());
		System.out.println("║ Name        : " + product.getName());
		System.out.println("║ Description : " + product.getDescription());
		System.out.println("║ Brand       : " + product.getBrand());
		System.out.println("║ Price       : ₹" + product.getPrice());
		System.out.println("║ Category    : " + product.getCategory());
		System.out.println("║ Release Date: " + (product.getReleaseDate() != null
				? dateFormat.format(product.getReleaseDate())
				: "N/A"));
		System.out.println("║ Available   : "
				+ (product.isProductAvailable() ? "✅ Yes" : "❌ No"));
		System.out.println("║ Stock       : " + product.getStockQuantity());
		System.out.println("║ Image       : "
				+ (product.getImageName() != null ? product.getImageName() : "No Image"));
		System.out.println("╚══════════════════════════════════════════════════╝");
	}

	private void addNewProduct() {
		try {
			System.out.println("\n➕ ADD NEW PRODUCT");
			System.out.println("─".repeat(40));

			Product product = new Product();

			System.out.print("📝 Product Name: ");
			product.setName(scanner.nextLine());

			System.out.print("📄 Description: ");
			product.setDescription(scanner.nextLine());

			System.out.print("🏷️ Brand: ");
			product.setBrand(scanner.nextLine());

			System.out.print("💰 Price: ₹");
			product.setPrice(new BigDecimal(scanner.nextLine()));

			System.out.print("📂 Category: ");
			product.setCategory(scanner.nextLine());

			System.out.print("📅 Release Date (yyyy-mm-dd): ");
			String dateStr = scanner.nextLine();

			if (!dateStr.trim().isEmpty()) {
				try {
					Date releaseDate = dateFormat.parse(dateStr);
					product.setReleaseDate(releaseDate);
				} catch (ParseException e) {
					System.out.println("⚠️ Invalid date format, using current date");
					product.setReleaseDate(new Date());
				}
			} else {
				product.setReleaseDate(new Date());
			}

			System.out.print("✅ Is Available (true/false): ");
			product.setProductAvailable(
					Boolean.parseBoolean(scanner.nextLine()));

			System.out.print("📦 Stock Quantity: ");
			product.setStockQuantity(
					Integer.parseInt(scanner.nextLine()));

			Product savedProduct = productService.saveProduct(product, null);

			System.out.println(
					"🎉 Product added successfully with ID: "
							+ savedProduct.getId());

		} catch (Exception e) {
			System.out.println("❌ Error adding product: " + e.getMessage());
		}
	}

	private void updateProduct() {
		int id = getIntInput("🔢 Enter Product ID to update: ");

		try {
			Product existingProduct = productService.getProductById(id);

			if (existingProduct == null) {
				System.out.println("❌ Product not found with ID: " + id);
				return;
			}

			System.out.println(
					"\n✏️ UPDATE PRODUCT (Press Enter to keep current value)");
			System.out.println("─".repeat(50));

			displayProductDetails(existingProduct);

			Product updatedProduct = new Product();
			updatedProduct.setId(id);

			System.out.print(
					"📝 New Name [" + existingProduct.getName() + "]: ");
			String name = scanner.nextLine();

			updatedProduct.setName(
					name.isEmpty() ? existingProduct.getName() : name);

			System.out.print(
					"📄 New Description ["
							+ existingProduct.getDescription() + "]: ");
			String desc = scanner.nextLine();

			updatedProduct.setDescription(
					desc.isEmpty() ? existingProduct.getDescription() : desc);

			System.out.print(
					"🏷️ New Brand [" + existingProduct.getBrand() + "]: ");
			String brand = scanner.nextLine();

			updatedProduct.setBrand(
					brand.isEmpty() ? existingProduct.getBrand() : brand);

			System.out.print(
					"💰 New Price [₹"
							+ existingProduct.getPrice() + "]: ");
			String priceStr = scanner.nextLine();

			updatedProduct.setPrice(
					priceStr.isEmpty()
							? existingProduct.getPrice()
							: new BigDecimal(priceStr));

			System.out.print(
					"📂 New Category ["
							+ existingProduct.getCategory() + "]: ");
			String category = scanner.nextLine();

			updatedProduct.setCategory(
					category.isEmpty()
							? existingProduct.getCategory()
							: category);

			System.out.print(
					"✅ New Availability ["
							+ existingProduct.isProductAvailable() + "]: ");
			String availStr = scanner.nextLine();

			updatedProduct.setProductAvailable(
					availStr.isEmpty()
							? existingProduct.isProductAvailable()
							: Boolean.parseBoolean(availStr));

			System.out.print(
					"📦 New Stock ["
							+ existingProduct.getStockQuantity() + "]: ");
			String stockStr = scanner.nextLine();

			updatedProduct.setStockQuantity(
					stockStr.isEmpty()
							? existingProduct.getStockQuantity()
							: Integer.parseInt(stockStr));

			// Keep existing values for fields not being updated
			updatedProduct.setReleaseDate(existingProduct.getReleaseDate());
			updatedProduct.setImageName(existingProduct.getImageName());
			updatedProduct.setImageType(existingProduct.getImageType());
			updatedProduct.setImageData(existingProduct.getImageData());

			Product result =
					productService.updateProduct(id, updatedProduct, null);

			if (result != null) {
				System.out.println("🎉 Product updated successfully!");
				displayProductDetails(result);
			} else {
				System.out.println("❌ Failed to update product.");
			}

		} catch (Exception e) {
			System.out.println("❌ Error updating product: " + e.getMessage());
		}
	}

	private void deleteProduct() {
		int id = getIntInput("🔢 Enter Product ID to delete: ");

		try {
			Product product = productService.getProductById(id);

			if (product == null) {
				System.out.println("❌ Product not found with ID: " + id);
				return;
			}

			displayProductDetails(product);

			System.out.print(
					"\n⚠️ Are you sure you want to delete this product? (yes/no): ");

			String confirmation = scanner.nextLine();

			if (confirmation.equalsIgnoreCase("yes")
					|| confirmation.equalsIgnoreCase("y")) {

				productService.deleteProduct(id);
				System.out.println("🗑️ Product deleted successfully!");

			} else {
				System.out.println("❌ Deletion cancelled.");
			}

		} catch (Exception e) {
			System.out.println("❌ Error deleting product: " + e.getMessage());
		}
	}

	private void searchProducts() {
		System.out.print("🔍 Enter search keyword: ");
		String keyword = scanner.nextLine();

		if (keyword.trim().isEmpty()) {
			System.out.println("❌ Please enter a valid keyword.");
			return;
		}

		try {
			List<Product> products =
					productService.searchProducts(keyword);

			System.out.println(
					"\n🔎 SEARCH RESULTS for '" + keyword + "':");

			System.out.println("─".repeat(80));

			if (products.isEmpty()) {
				System.out.println(
						"📪 No products found matching '" + keyword + "'");
				return;
			}

			System.out.printf(
					"%-5s %-20s %-30s %-15s %-10s%n",
					"ID",
					"NAME",
					"DESCRIPTION",
					"BRAND",
					"PRICE");

			System.out.println("─".repeat(80));

			for (Product product : products) {
				System.out.printf(
						"%-5d %-20s %-30s %-15s ₹%-9s%n",
						product.getId(),
						truncate(product.getName(), 20),
						truncate(product.getDescription(), 30),
						truncate(product.getBrand(), 15),
						product.getPrice());
			}

			System.out.println("─".repeat(80));
			System.out.println("📊 Found " + products.size() + " products");

		} catch (Exception e) {
			System.out.println("❌ Error searching products: " + e.getMessage());
		}
	}

	private int getIntInput(String prompt) {
		while (true) {
			try {
				System.out.print(prompt);
				String input = scanner.nextLine();
				return Integer.parseInt(input);

			} catch (NumberFormatException e) {
				System.out.println(
						"❌ Invalid number, please try again.");
			}
		}
	}

	private String truncate(String str, int maxLength) {
		if (str == null) {
			return "N/A";
		}

		return str.length() > maxLength
				? str.substring(0, maxLength) + "..."
				: str;
	}
}
