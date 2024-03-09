using Serilog.Context;

namespace tuvarna_ecommerce_system.Middleware
{
    public class CorrelationIdMiddleware
{
    private const string CorrelationIdHeaderName = "X-Correlation-ID";
    private readonly RequestDelegate _next;

    public CorrelationIdMiddleware(RequestDelegate next)
    {
        _next = next;
    }

        public async Task Invoke(HttpContext context)
        {
            var correlationId = context.Request.Headers[CorrelationIdHeaderName].FirstOrDefault() ?? Guid.NewGuid().ToString();

            // Set in HttpContext.Items for downstream access
            context.Items[CorrelationIdHeaderName] = correlationId;

            // Use a logging scope to include CorrelationId in logs generated during this request
            using (LogContext.PushProperty("CorrelationId", correlationId))
            {
                context.Response.Headers[CorrelationIdHeaderName] = correlationId;
                await _next(context);
            }
        }
    }

}
