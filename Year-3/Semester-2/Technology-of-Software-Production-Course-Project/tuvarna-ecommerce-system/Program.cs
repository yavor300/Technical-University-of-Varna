using Microsoft.EntityFrameworkCore;
using tuvarna_ecommerce_system.Data;
using tuvarna_ecommerce_system.Data.Repositories;
using tuvarna_ecommerce_system.Repository.Implementation;
using tuvarna_ecommerce_system.Service.Implementation;
using tuvarna_ecommerce_system.Service;
using Microsoft.AspNetCore.Mvc;
using Serilog;
using tuvarna_ecommerce_system.Middleware;
using tuvarna_ecommerce_system.Repository;
using Microsoft.AspNetCore.Identity;
using tuvarna_ecommerce_system.Models.Entities;
using tuvarna_ecommerce_system.Models.DTOs;
using tuvarna_ecommerce_system.Models.Entities.Enums;
using Microsoft.AspNetCore.Authentication.JwtBearer;
using Microsoft.IdentityModel.Tokens;
using System.Text;

var builder = WebApplication.CreateBuilder(args);

// Add services to the container.
builder.Services.AddDbContext<EcommerceDbContext>(options =>
    options.UseSqlServer(builder.Configuration.GetConnectionString("DevEcommerceDb")), ServiceLifetime.Scoped);

builder.Services.AddControllers();
// Learn more about configuring Swagger/OpenAPI at https://aka.ms/aspnetcore/swashbuckle
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();
builder.Services.AddScoped<ICategoryRepository, CategoryRepository>();
builder.Services.AddScoped<ICategoryService, CategoryService>();
builder.Services.AddScoped<ITagRepository, TagRepository>();
builder.Services.AddScoped<IProductRepository, ProductRepository>();
builder.Services.AddScoped<ISaleRepository, SaleRepository>();
builder.Services.AddScoped<ISaleItemRepository, SaleItemRepository>();
builder.Services.AddScoped<IProductImageRepository, ProductImageRepository>();
builder.Services.AddScoped<IProductInventoryRepository, ProductInventoryRepository>();
builder.Services.AddScoped<ITagService, TagService>();
builder.Services.AddScoped<IProductService, ProductService>();
builder.Services.AddScoped<IProductImageService, ProductImageService>();
builder.Services.AddScoped<IProductInventoryService, ProductInventoryService>();
builder.Services.AddScoped<ISaleService, SaleService>();
builder.Services.AddScoped<ISaleItemService, SaleItemService>();
builder.Services.AddScoped<IUserRepository, UserRepository>();
builder.Services.AddScoped<IUserService, UserService>();
builder.Services.AddScoped<IPasswordHasher<User>, PasswordHasher<User>>();
builder.Services.AddScoped<AuthService>();
builder.Services.AddCors(options =>
{
    options.AddPolicy("AllowSpecificOrigin",
        builder => builder.WithOrigins("http://localhost:4200")
            .AllowAnyHeader()
            .AllowAnyMethod());
});
builder.Services.Configure<ApiBehaviorOptions>(options =>
{
    options.SuppressModelStateInvalidFilter = true;
});
builder.Services.AddAuthentication(options =>
{
    options.DefaultAuthenticateScheme = JwtBearerDefaults.AuthenticationScheme;
    options.DefaultChallengeScheme = JwtBearerDefaults.AuthenticationScheme;
})
.AddJwtBearer(options =>
{
    options.TokenValidationParameters = new TokenValidationParameters
    {
        ValidateIssuer = true,
        ValidateAudience = true,
        ValidateLifetime = true,
        ValidateIssuerSigningKey = true,
        ValidIssuer = builder.Configuration["Jwt:Issuer"],
        ValidAudience = builder.Configuration["Jwt:Audience"],
        IssuerSigningKey = new SymmetricSecurityKey(Encoding.UTF8.GetBytes(builder.Configuration["Jwt:Key"]))
    };
});

Log.Logger = new LoggerConfiguration()
    .MinimumLevel.Override("Microsoft", Serilog.Events.LogEventLevel.Warning)
    .Enrich.FromLogContext()
    .WriteTo.File("logs/tuvarna-ecommerce-system.txt", rollingInterval: RollingInterval.Day)
    .CreateLogger();
builder.Host.UseSerilog();

var app = builder.Build();
app.UseCors("AllowSpecificOrigin");
app.UseMiddleware<CorrelationIdMiddleware>();
app.UseSerilogRequestLogging(options => {
    options.EnrichDiagnosticContext = (diagnosticContext, httpContext) => {
        if (httpContext.Items.ContainsKey("X-Correlation-ID"))
        {
            diagnosticContext.Set("CorrelationId", httpContext.Items["X-Correlation-ID"]);
        }
    };
    options.MessageTemplate = "Handled {RequestMethod} {RequestPath} responded {StatusCode} in {Elapsed:0.0000} ms {CorrelationId}";
});

// Ensure the database is up-to-date with the latest migrations
using (var scope = app.Services.CreateScope())
{
    var services = scope.ServiceProvider;

    var dbContext = services.GetRequiredService<EcommerceDbContext>();
    dbContext.Database.Migrate();
}

await InitializeAdminUser(app.Services);

// Configure the HTTP request pipeline.
if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}

app.UseAuthentication();
app.UseAuthorization();

app.UseHttpsRedirection();

app.UseAuthorization();

app.MapControllers();



app.Run();

static async Task InitializeAdminUser(IServiceProvider serviceProvider)
{
    using (var scope = serviceProvider.CreateScope())
    {
        var userService = scope.ServiceProvider.GetRequiredService<IUserService>();
        var userRepository = scope.ServiceProvider.GetRequiredService<IUserRepository>();
        try
        {
            if (await userRepository.FindByEmail("admin@tek.bg") == null)
            {
                var adminCreateDto = new UserCreateDTO
                {
                    Username = "admin",
                    Email = "admin@tek.bg",
                    Password = "admin",
                    Role = RoleEnum.ADMIN.ToString()
                };

                await userService.CreateAsync(adminCreateDto);
                Console.WriteLine($"Created administrator.");
            }
        }
        catch (Exception ex)
        {
            Console.WriteLine($"Failed to create administrator: {ex.Message}");
        }
    }
}
