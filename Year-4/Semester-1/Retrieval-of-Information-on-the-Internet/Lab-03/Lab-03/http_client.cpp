#include <winsock2.h>
#include <ws2tcpip.h>
#include <iostream>
#include <string>
#include <fstream>

#pragma comment(lib, "ws2_32.lib")

void fetch_html(const std::string& server, const std::string& path = "/") {
    WSADATA wsaData;
    SOCKET ConnectSocket = INVALID_SOCKET;
    struct addrinfo hints, *result = nullptr;
    char recvbuf[4096];
    int recvbuflen = 4096;

    // Initialize Winsock
    int status = WSAStartup(MAKEWORD(2, 2), &wsaData);
    if (status != 0) {
        std::cerr << "WSAStartup failed: " << status << std::endl;
        return;
    }

    // Setup hints structure for getaddrinfo
    ZeroMemory(&hints, sizeof(hints));
    hints.ai_family = AF_INET;         // IPv4
    hints.ai_socktype = SOCK_STREAM;   // TCP socket
    hints.ai_protocol = IPPROTO_TCP;   // TCP protocol

    // Resolve hostname or IP address
    status = getaddrinfo(server.c_str(), "80", &hints, &result);
    if (status != 0) {
        std::cerr << "getaddrinfo failed: " << WSAGetLastError() << std::endl;
        WSACleanup();
        return;
    }

    // Extract and print IP address
    struct sockaddr_in* addr = (struct sockaddr_in*)result->ai_addr;
    char ip_str[INET_ADDRSTRLEN];
    inet_ntop(AF_INET, &(addr->sin_addr), ip_str, INET_ADDRSTRLEN);
    std::cout << "Resolved IP Address: " << ip_str << std::endl;

    // Create a socket
    ConnectSocket = socket(result->ai_family, result->ai_socktype, result->ai_protocol);
    if (ConnectSocket == INVALID_SOCKET) {
        std::cerr << "Error at socket(): " << WSAGetLastError() << std::endl;
        freeaddrinfo(result);
        WSACleanup();
        return;
    }

    // Connect to server
    status = connect(ConnectSocket, result->ai_addr, static_cast<int>(result->ai_addrlen));
    if (status == SOCKET_ERROR) {
        std::cerr << "Unable to connect to server: " << WSAGetLastError() << std::endl;
        closesocket(ConnectSocket);
        freeaddrinfo(result);
        WSACleanup();
        return;
    }

    freeaddrinfo(result); // Address info is no longer needed

    // Form the HTTP GET request
    std::string request = "GET " + path + " HTTP/1.1\r\n";
    request += "Host: " + server + "\r\n";
    request += "Connection: close\r\n\r\n";

    // Send the request
    status = send(ConnectSocket, request.c_str(), static_cast<int>(request.length()), 0);
    if (status == SOCKET_ERROR) {
        std::cerr << "Send failed: " << WSAGetLastError() << std::endl;
        closesocket(ConnectSocket);
        WSACleanup();
        return;
    }

    // Open file to write the HTML content (after the <html> tag)
    std::ofstream outfile("response.html");
    if (!outfile) {
        std::cerr << "Could not open file to write response.\n";
        closesocket(ConnectSocket);
        WSACleanup();
        return;
    }

    bool found_html = false;
    std::cout << "Writing HTML content to response.html...\n";
    do {
        status = recv(ConnectSocket, recvbuf, recvbuflen, 0);
        if (status > 0) {
            if (!found_html) {
                // Search for the <html> tag in the buffer
                std::string buffer_str(recvbuf, status);
                size_t html_pos = buffer_str.find("<html");

                if (html_pos != std::string::npos) {
                    // Start writing from <html> tag onwards
                    outfile << buffer_str.substr(html_pos);
                    found_html = true;
                }
            } else {
                // Continue writing the remaining content
                outfile.write(recvbuf, status);
            }
        } else if (status == 0) {
            std::cout << "\nConnection closed\n";
        } else {
            std::cerr << "Recv failed: " << WSAGetLastError() << std::endl;
        }
    } while (status > 0);

    // Close the file and cleanup
    outfile.close();
    closesocket(ConnectSocket);
    WSACleanup();
}

void parse_server_and_path(const std::string& input, std::string& server, std::string& path) {
    std::string url = input;

    // Remove "http://" or "https://"
    const std::string http_prefix = "http://";
    const std::string https_prefix = "https://";

    if (url.rfind(http_prefix, 0) == 0) {
        url = url.substr(http_prefix.length());
    } else if (url.rfind(https_prefix, 0) == 0) {
        url = url.substr(https_prefix.length());
    }

    // Extract server and path
    size_t pos = url.find('/');
    if (pos != std::string::npos) {
        server = url.substr(0, pos);
        path = url.substr(pos);
    } else {
        server = url;
        path = "/";
    }
}

int main() {
    std::string input;
    std::string server;
    std::string path;

    std::cout << "Enter server and path (e.g., http://crawlertest.cs.tu-varna.bg/TestIIR.html): ";
    std::getline(std::cin, input);

    // Parse the server and path from the input
    parse_server_and_path(input, server, path);

    fetch_html(server, path);
    std::getchar();
    return 0;
}
