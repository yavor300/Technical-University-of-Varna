using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace tuvarna_ecommerce_system.Migrations
{
    /// <inheritdoc />
    public partial class AddPropertiesToSale : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<string>(
                name: "CompanyName",
                table: "Sales",
                type: "nvarchar(100)",
                maxLength: 100,
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "Country",
                table: "Sales",
                type: "nvarchar(100)",
                maxLength: 100,
                nullable: false,
                defaultValue: "");

            migrationBuilder.AddColumn<string>(
                name: "Email",
                table: "Sales",
                type: "nvarchar(100)",
                maxLength: 100,
                nullable: false,
                defaultValue: "");

            migrationBuilder.AddColumn<string>(
                name: "FirstName",
                table: "Sales",
                type: "nvarchar(50)",
                maxLength: 50,
                nullable: false,
                defaultValue: "");

            migrationBuilder.AddColumn<string>(
                name: "LastName",
                table: "Sales",
                type: "nvarchar(50)",
                maxLength: 50,
                nullable: false,
                defaultValue: "");

            migrationBuilder.AddColumn<string>(
                name: "OrderNotes",
                table: "Sales",
                type: "nvarchar(500)",
                maxLength: 500,
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "PhoneNumber",
                table: "Sales",
                type: "nvarchar(20)",
                maxLength: 20,
                nullable: false,
                defaultValue: "");

            migrationBuilder.AddColumn<string>(
                name: "State",
                table: "Sales",
                type: "nvarchar(50)",
                maxLength: 50,
                nullable: false,
                defaultValue: "");

            migrationBuilder.AddColumn<string>(
                name: "StreetAddress",
                table: "Sales",
                type: "nvarchar(100)",
                maxLength: 100,
                nullable: false,
                defaultValue: "");

            migrationBuilder.AddColumn<string>(
                name: "Town",
                table: "Sales",
                type: "nvarchar(50)",
                maxLength: 50,
                nullable: false,
                defaultValue: "");

            migrationBuilder.AddColumn<string>(
                name: "ZipCode",
                table: "Sales",
                type: "nvarchar(20)",
                maxLength: 20,
                nullable: false,
                defaultValue: "");
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "CompanyName",
                table: "Sales");

            migrationBuilder.DropColumn(
                name: "Country",
                table: "Sales");

            migrationBuilder.DropColumn(
                name: "Email",
                table: "Sales");

            migrationBuilder.DropColumn(
                name: "FirstName",
                table: "Sales");

            migrationBuilder.DropColumn(
                name: "LastName",
                table: "Sales");

            migrationBuilder.DropColumn(
                name: "OrderNotes",
                table: "Sales");

            migrationBuilder.DropColumn(
                name: "PhoneNumber",
                table: "Sales");

            migrationBuilder.DropColumn(
                name: "State",
                table: "Sales");

            migrationBuilder.DropColumn(
                name: "StreetAddress",
                table: "Sales");

            migrationBuilder.DropColumn(
                name: "Town",
                table: "Sales");

            migrationBuilder.DropColumn(
                name: "ZipCode",
                table: "Sales");
        }
    }
}
