/*
 * Copyright (C) 2018-2023 Velocity Contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.velocitypowered.proxy.util;

import com.google.common.base.Preconditions;
import com.google.common.net.InetAddresses;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URI;

/**
 * Utilities to parse addresses.
 */
public final class AddressUtil {

  private static final int DEFAULT_MINECRAFT_PORT = 25565;

  private AddressUtil() {
    throw new AssertionError();
  }

  /**
   * Attempts to parse an IP address of the form {@code 127.0.0.1:25565}. The returned
   * {@link InetSocketAddress} is not resolved.
   *
   * @param ip the IP to parse
   * @return the parsed address
   */
  public static InetSocketAddress parseAddress(String ip) {
    Preconditions.checkNotNull(ip, "ip");
    String host;
    int port = DEFAULT_MINECRAFT_PORT;

    int colonIndex = ip.lastIndexOf(':');
    if (colonIndex != -1 && colonIndex != ip.length() - 1) {
      host = ip.substring(0, colonIndex);
      try {
        port = Integer.parseInt(ip.substring(colonIndex + 1));
      } catch (NumberFormatException e) {
        throw new IllegalStateException("Invalid port in address: " + ip);
      }
    } else {
      host = ip;
    }

    try {
      InetAddress ia = InetAddresses.forUriString(host);
      return new InetSocketAddress(ia, port);
    } catch (IllegalArgumentException e) {
      return InetSocketAddress.createUnresolved(host, port);
    }
  }

  /**
   * Attempts to parse an IP address of the form {@code 127.0.0.1:25565}. The returned
   * {@link InetSocketAddress} is resolved.
   *
   * @param ip the IP to parse
   * @return the parsed address
   */
  public static InetSocketAddress parseAndResolveAddress(String ip) {
    Preconditions.checkNotNull(ip, "ip");
    String host;
    int port = DEFAULT_MINECRAFT_PORT;

    int colonIndex = ip.lastIndexOf(':');
    if (colonIndex != -1 && colonIndex != ip.length() - 1) {
      host = ip.substring(0, colonIndex);
      try {
        port = Integer.parseInt(ip.substring(colonIndex + 1));
      } catch (NumberFormatException e) {
        throw new IllegalStateException("Invalid port in address: " + ip);
      }
    } else {
      host = ip;
    }

    return new InetSocketAddress(host, port);
  }
}
