import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import { AuthProvider, useAuth } from './context/AuthContext';
import Navbar from './components/Navbar';
import PrivateRoute from './components/PrivateRoute';
import Login from './pages/Login';
import Register from './pages/Register';
import Dashboard from './pages/Dashboard';
import LostItems from './pages/LostItems';
import FoundItems from './pages/FoundItems';
import Claims from './pages/Claims';
import Notifications from './pages/Notifications';
import AdminDashboard from './pages/AdminDashboard';

function AppRoutes() {
  const { token } = useAuth();
  return (
    <>
      {token && <Navbar />}
      <Routes>
        <Route path="/login" element={token ? <Navigate to="/" /> : <Login />} />
        <Route path="/register" element={token ? <Navigate to="/" /> : <Register />} />
        <Route path="/" element={<PrivateRoute><Dashboard /></PrivateRoute>} />
        <Route path="/lost-items" element={<PrivateRoute><LostItems /></PrivateRoute>} />
        <Route path="/found-items" element={<PrivateRoute><FoundItems /></PrivateRoute>} />
        <Route path="/claims" element={<PrivateRoute><Claims /></PrivateRoute>} />
        <Route path="/notifications" element={<PrivateRoute><Notifications /></PrivateRoute>} />
        <Route path="/admin" element={<PrivateRoute><AdminDashboard /></PrivateRoute>} />
        <Route path="*" element={<Navigate to="/" />} />
      </Routes>
    </>
  );
}

export default function App() {
  return (
    <AuthProvider>
      <BrowserRouter>
        <AppRoutes />
      </BrowserRouter>
    </AuthProvider>
  );
}
