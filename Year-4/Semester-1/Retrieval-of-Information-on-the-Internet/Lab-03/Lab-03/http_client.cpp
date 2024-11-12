#include <winsock2.h>

#include <ws2tcpip.h>

#include <iostream>

#include <string>

#pragma comment(lib, "ws2_32.lib")

void fetch_html(const std::string & server,
  const std::string & path = "/") {
  WSADATA wsaData;
  SOCKET ConnectSocket = INVALID_SOCKET;
  struct addrinfo hints, * result = nullptr;
  char recvbuf[4096];
  int recvbuflen = 4096;

  // Initialize Winsock
  int status = WSAStartup(MAKEWORD(2, 2), & wsaData);
  if (status != 0) {
    std::cerr << "WSAStartup failed: " << status << std::endl;
    return;
  }

  // Setup hints structure for getaddrinfo
  ZeroMemory( & hints, sizeof(hints));
  hints.ai_family = AF_INET; // IPv4
  hints.ai_socktype = SOCK_STREAM; // TCP socket
  hints.ai_protocol = IPPROTO_TCP; // TCP protocol

  // Resolve hostname or IP address
  status = getaddrinfo(server.c_str(), "80", & hints, & result);
  if (status != 0) {
    std::cerr << "getaddrinfo failed: " << WSAGetLastError() << std::endl;
    WSACleanup();
    return;
  }

  // Create a socket
  ConnectSocket = socket(result -> ai_family, result -> ai_socktype, result -> ai_protocol);
  if (ConnectSocket == INVALID_SOCKET) {
    std::cerr << "Error at socket(): " << WSAGetLastError() << std::endl;
    freeaddrinfo(result);
    WSACleanup();
    return;
  }

  // Connect to server
  status = connect(ConnectSocket, result -> ai_addr, static_cast < int > (result -> ai_addrlen));
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
  status = send(ConnectSocket, request.c_str(), static_cast < int > (request.length()), 0);
  if (status == SOCKET_ERROR) {
    std::cerr << "Send failed: " << WSAGetLastError() << std::endl;
    closesocket(ConnectSocket);
    WSACleanup();
    return;
  }

  std::cout << "Received HTML content:\n\n";
  do {
    status = recv(ConnectSocket, recvbuf, recvbuflen, 0);
    if (status > 0) {
      std::cout.write(recvbuf, status);
    } else if (status == 0) {
      std::cout << "\nConnection closed\n";
    } else {
      std::cerr << "Recv failed: " << WSAGetLastError() << std::endl;
    }
  } while (status > 0);

  closesocket(ConnectSocket);
  WSACleanup();
}

int main() {
  std::string server;
  std::string path;

  std::cout << "Enter server hostname or IP: ";
  std::cin >> server;
  std::cout << "Enter path (default is /): ";
  std::cin.ignore();
  std::getline(std::cin, path);

  if (path.empty()) {
    path = "/";
  }

  fetch_html(server, path);
  std::getchar();
  return 0;
}
