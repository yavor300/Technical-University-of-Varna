using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace tuvarna_ecommerce_system.Migrations
{
    /// <inheritdoc />
    public partial class AddShippingTypeToSale : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<int>(
                name: "ShippingType",
                table: "Sales",
                type: "int",
                nullable: false,
                defaultValue: 0);
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "ShippingType",
                table: "Sales");
        }
    }
}
