import axios from 'axios';

const api = axios.create({ baseURL: 'http://localhost:8051/api' });

api.interceptors.request.use((config) => {
  const token = localStorage.getItem('token');
  if (token) config.headers.Authorization = `Bearer ${token}`;
  return config;
});

export const authAPI = {
  login: (data) => api.post('/auth/login', data),
  register: (data) => api.post('/auth/register', data),
};

export const lostItemAPI = {
  getAll: () => api.get('/lost-items'),
  getById: (id) => api.get(`/lost-items/${id}`),
  create: (data) => api.post('/lost-items', data),
  update: (id, data) => api.put(`/lost-items/${id}`, data),
  delete: (id) => api.delete(`/lost-items/${id}`),
  search: (keyword) => api.get(`/lost-items/search?keyword=${keyword}`),
  getByUser: (userId) => api.get(`/lost-items/user/${userId}`),
};

export const foundItemAPI = {
  getAll: () => api.get('/found-items'),
  getById: (id) => api.get(`/found-items/${id}`),
  create: (data) => api.post('/found-items', data),
  update: (id, data) => api.put(`/found-items/${id}`, data),
  delete: (id) => api.delete(`/found-items/${id}`),
  search: (keyword) => api.get(`/found-items/search?keyword=${keyword}`),
  getByUser: (userId) => api.get(`/found-items/user/${userId}`),
};

export const claimAPI = {
  getAll: () => api.get('/claims'),
  getById: (id) => api.get(`/claims/${id}`),
  create: (data) => api.post('/claims', data),
  update: (id, data) => api.put(`/claims/${id}`, data),
  delete: (id) => api.delete(`/claims/${id}`),
  approve: (id) => api.put(`/claims/${id}/approve`),
  reject: (id) => api.put(`/claims/${id}/reject`),
  getByUser: (userId) => api.get(`/claims/user/${userId}`),
  getPending: () => api.get('/claims/pending'),
};

export const notificationAPI = {
  getAll: (userId) => api.get(`/notifications/user/${userId}`),
  getUnread: (userId) => api.get(`/notifications/user/${userId}/unread`),
  getUnreadCount: (userId) => api.get(`/notifications/user/${userId}/unread-count`),
  markRead: (id) => api.put(`/notifications/${id}/read`),
  markAllRead: (userId) => api.put(`/notifications/user/${userId}/mark-all-read`),
};

export const uploadAPI = {
  upload: (file) => {
    const formData = new FormData();
    formData.append('file', file);
    return api.post('/upload', formData, { headers: { 'Content-Type': 'multipart/form-data' } });
  },
};

export const dashboardAPI = {
  getAdminStats: () => api.get('/dashboard/admin/stats'),
  getUserStats: (userId) => api.get(`/dashboard/user/${userId}/stats`),
};

export default api;
