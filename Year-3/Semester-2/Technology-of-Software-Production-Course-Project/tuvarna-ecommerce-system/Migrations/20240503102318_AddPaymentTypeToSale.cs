using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace tuvarna_ecommerce_system.Migrations
{
    /// <inheritdoc />
    public partial class AddPaymentTypeToSale : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<int>(
                name: "PaymentType",
                table: "Sales",
                type: "int",
                nullable: false,
                defaultValue: 0);
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "PaymentType",
                table: "Sales");
        }
    }
}
