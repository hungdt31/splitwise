import { API_BASE_URL } from '../config';

export type HttpMethod = 'GET' | 'POST' | 'PATCH' | 'DELETE';

export interface ApiClientOptions {
  /** JWT access token (US-002) */
  accessToken?: string | null;
  method?: HttpMethod;
  body?: unknown;
  headers?: Record<string, string>;
}

/**
 * Fetch helper: base URL, JSON body, Authorization header.
 * Use inside React Query queryFn / mutationFn.
 */
export async function apiFetch<T>(
  path: string,
  options: ApiClientOptions = {}
): Promise<T> {
  const { accessToken, method = 'GET', body, headers = {} } = options;
  const url = `${API_BASE_URL}${path}`;

  const init: RequestInit = {
    method,
    headers: {
      Accept: 'application/json',
      ...headers,
    },
  };

  if (accessToken) {
    (init.headers as Record<string, string>)['Authorization'] =
      `Bearer ${accessToken}`;
  }

  if (body !== undefined && method !== 'GET') {
    (init.headers as Record<string, string>)['Content-Type'] =
      'application/json';
    init.body = JSON.stringify(body);
  }

  const res = await fetch(url, init);
  const text = await res.text();
  const data = text ? JSON.parse(text) : null;

  if (!res.ok) {
    const message =
      data?.message ?? `HTTP ${res.status} ${res.statusText}`;
    throw new Error(message);
  }

  return data as T;
}
