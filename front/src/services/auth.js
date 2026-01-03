const ROOT = process.env.REACT_APP_ROOT_PATH || '/';

const ACCESS_KEY = 'token';
const REFRESH_KEY = 'refreshToken';

export function setAccessToken(token) {
  let expires = (new Date(Date.now() + 86400 * 1000)).toUTCString();
  console.log("Setting access token in cookie: ", token);
  document.cookie = ACCESS_KEY + "=" + token + "; expires=" + expires;
}

export function getRefreshToken() {
  const fromStorage = localStorage.getItem(REFRESH_KEY);
  return fromStorage || null;
}

export function setRefreshToken(token) {
  if (token) {
    const value = token.startsWith('Bearer ') ? token.substring(7) : token;
    localStorage.setItem(REFRESH_KEY, value);
  } else localStorage.removeItem(REFRESH_KEY);
}

export async function refreshAccessToken() {
  const refreshFromStorage = getRefreshToken();
  let resp;
  if (refreshFromStorage) {
    resp = await fetch(ROOT + 'auth/refresh', {
      method: 'POST',
      headers: { 'Accept': 'application/json', 'Content-Type': 'application/json' },
      body: JSON.stringify({ accessToken: '', refreshToken: refreshFromStorage })
    });
  }

  const data = await resp.json();
  if (data.accessToken) setAccessToken(data.accessToken);
  if (data.refreshToken) setRefreshToken(data.refreshToken);
  return data.accessToken || null;
}

export default {
  setAccessToken,
  getRefreshToken,
  setRefreshToken,
  refreshAccessToken
};
