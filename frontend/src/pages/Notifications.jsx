import { useEffect, useState } from 'react';
import { notificationAPI } from '../api/api';
import { useAuth } from '../context/AuthContext';

export default function Notifications() {
  const { user } = useAuth();
  const [notifications, setNotifications] = useState([]);
  const userId = user?.id || user?.userId;

  const load = () => notificationAPI.getAll(userId).then((r) => setNotifications(r.data));

  useEffect(() => { if (userId) load(); }, [userId]);

  const markRead = async (id) => {
    await notificationAPI.markRead(id);
    load();
  };

  const markAllRead = async () => {
    await notificationAPI.markAllRead(userId);
    load();
  };

  return (
    <div style={styles.page}>
      <div style={styles.header}>
        <h2>Notifications</h2>
        <button style={styles.btn} onClick={markAllRead}>Mark All as Read</button>
      </div>
      {notifications.length === 0 && <p style={{ color: '#888' }}>No notifications.</p>}
      <div style={styles.list}>
        {notifications.map((n) => (
          <div key={n.id} style={{ ...styles.item, background: n.read ? '#fff' : '#e8f0fe' }}>
            <div style={styles.msg}>{n.message}</div>
            <div style={styles.meta}>{n.createdAt}</div>
            {!n.read && (
              <button style={styles.btnSm} onClick={() => markRead(n.id)}>Mark as Read</button>
            )}
          </div>
        ))}
      </div>
    </div>
  );
}

const styles = {
  page: { padding: '32px' },
  header: { display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '20px' },
  btn: { padding: '8px 16px', background: '#1a73e8', color: '#fff', border: 'none', borderRadius: '4px', cursor: 'pointer', fontWeight: 'bold' },
  list: { display: 'flex', flexDirection: 'column', gap: '10px', maxWidth: '700px' },
  item: { padding: '16px', borderRadius: '8px', boxShadow: '0 1px 4px rgba(0,0,0,0.08)', display: 'flex', justifyContent: 'space-between', alignItems: 'center', gap: '12px' },
  msg: { flex: 1, fontSize: '0.95rem', color: '#333' },
  meta: { fontSize: '0.8rem', color: '#888', whiteSpace: 'nowrap' },
  btnSm: { padding: '4px 12px', background: '#1a73e8', color: '#fff', border: 'none', borderRadius: '4px', cursor: 'pointer', fontSize: '0.8rem', whiteSpace: 'nowrap' },
};
