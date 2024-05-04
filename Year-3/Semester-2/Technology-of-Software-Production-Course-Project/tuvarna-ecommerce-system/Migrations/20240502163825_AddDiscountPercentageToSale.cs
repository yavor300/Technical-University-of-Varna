using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace tuvarna_ecommerce_system.Migrations
{
    /// <inheritdoc />
    public partial class AddDiscountPercentageToSale : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<int>(
                name: "DiscountPercentage",
                table: "Sales",
                type: "int",
                nullable: true);
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "DiscountPercentage",
                table: "Sales");
        }
    }
}
