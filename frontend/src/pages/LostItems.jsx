import { useEffect, useState } from 'react';
import { lostItemAPI } from '../api/api';
import { useAuth } from '../context/AuthContext';
import ItemForm from '../components/ItemForm';

export default function LostItems() {
  const { user } = useAuth();
  const [items, setItems] = useState([]);
  const [search, setSearch] = useState('');
  const [showForm, setShowForm] = useState(false);
  const [editing, setEditing] = useState(null);

  const load = () => lostItemAPI.getAll().then((r) => setItems(r.data));

  useEffect(() => { load(); }, []);

  const handleSearch = async (e) => {
    e.preventDefault();
    if (!search.trim()) return load();
    const res = await lostItemAPI.search(search);
    setItems(res.data);
  };

  const handleDelete = async (id) => {
    if (!confirm('Delete this item?')) return;
    await lostItemAPI.delete(id);
    load();
  };

  const handleSave = async (data) => {
    if (editing) {
      await lostItemAPI.update(editing.id, data);
    } else {
      await lostItemAPI.create({ ...data, userId: user?.id || user?.userId });
    }
    setShowForm(false);
    setEditing(null);
    load();
  };

  return (
    <div style={styles.page}>
      <div style={styles.header}>
        <h2>Lost Items</h2>
        <button style={styles.btn} onClick={() => { setEditing(null); setShowForm(true); }}>+ Report Lost Item</button>
      </div>
      <form onSubmit={handleSearch} style={styles.searchBar}>
        <input style={styles.searchInput} placeholder="Search by keyword..." value={search}
          onChange={(e) => setSearch(e.target.value)} />
        <button style={styles.btn} type="submit">Search</button>
        <button style={styles.btnSecondary} type="button" onClick={() => { setSearch(''); load(); }}>Clear</button>
      </form>
      {(showForm || editing) && (
        <ItemForm
          type="lost"
          initial={editing}
          onSave={handleSave}
          onCancel={() => { setShowForm(false); setEditing(null); }}
        />
      )}
      <div style={styles.grid}>
        {items.map((item) => (
          <div key={item.id} style={styles.card}>
            {item.imageUrl && <img src={item.imageUrl} alt={item.itemName} style={styles.img} />}
            <div style={styles.cardBody}>
              <h3 style={styles.itemName}>{item.itemName}</h3>
              <span style={{ ...styles.badge, background: item.status === 'FOUND' ? '#34a853' : '#ea4335' }}>{item.status}</span>
              <p style={styles.meta}><b>Category:</b> {item.category}</p>
              <p style={styles.meta}><b>Location:</b> {item.locationLost}</p>
              <p style={styles.meta}><b>Date:</b> {item.dateLost}</p>
              <p style={styles.desc}>{item.description}</p>
              <p style={styles.meta}><b>Reported by:</b> {item.userName}</p>
              <div style={styles.cardActions}>
                <button style={styles.btnSm} onClick={() => { setEditing(item); setShowForm(false); }}>Edit</button>
                <button style={{ ...styles.btnSm, background: '#ea4335' }} onClick={() => handleDelete(item.id)}>Delete</button>
              </div>
            </div>
          </div>
        ))}
      </div>
      {items.length === 0 && <p style={{ color: '#888', marginTop: '24px' }}>No lost items found.</p>}
    </div>
  );
}

const styles = {
  page: { padding: '32px' },
  header: { display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '16px' },
  searchBar: { display: 'flex', gap: '8px', marginBottom: '24px' },
  searchInput: { flex: 1, padding: '8px 12px', border: '1px solid #ccc', borderRadius: '4px' },
  btn: { padding: '8px 16px', background: '#1a73e8', color: '#fff', border: 'none', borderRadius: '4px', cursor: 'pointer', fontWeight: 'bold' },
  btnSecondary: { padding: '8px 16px', background: '#eee', color: '#333', border: 'none', borderRadius: '4px', cursor: 'pointer' },
  grid: { display: 'flex', flexWrap: 'wrap', gap: '20px' },
  card: { background: '#fff', borderRadius: '8px', boxShadow: '0 2px 8px rgba(0,0,0,0.08)', width: '280px', overflow: 'hidden' },
  img: { width: '100%', height: '160px', objectFit: 'cover' },
  cardBody: { padding: '16px' },
  itemName: { margin: '0 0 8px', fontSize: '1.1rem' },
  badge: { display: 'inline-block', color: '#fff', padding: '2px 10px', borderRadius: '12px', fontSize: '0.75rem', marginBottom: '8px' },
  meta: { margin: '4px 0', fontSize: '0.85rem', color: '#555' },
  desc: { fontSize: '0.85rem', color: '#666', margin: '8px 0' },
  cardActions: { display: 'flex', gap: '8px', marginTop: '12px' },
  btnSm: { padding: '5px 12px', background: '#1a73e8', color: '#fff', border: 'none', borderRadius: '4px', cursor: 'pointer', fontSize: '0.85rem' },
};
