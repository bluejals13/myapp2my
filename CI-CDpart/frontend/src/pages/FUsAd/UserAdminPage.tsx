import { useEffect, useState, useMemo, useCallback } from "react";
import { apiFetch } from "../../api";
import { useAuth } from "../../auth/AuthContext";
import styles from "./UserAdminPage.module.css";

type UserStatus = "ACTIVE" | "INACTIVE" | "BLOCKED";

type User = {
  id: number;
  username: string;
  permissions?: string[];
  status: UserStatus;
};

export default function UserAdminPage() {
  const { hasPermission, isLoading: authLoading } = useAuth();

  const canRead = hasPermission("USER_READ");
  const canUpdate = hasPermission("USER_UPDATE");
  const canDelete = hasPermission("USER_DELETE");

  const [statusLoading, setStatusLoading] = useState<number | null>(null);
  const [actionLoading, setActionLoading] = useState<number | null>(null);
  
  const [users, setUsers] = useState<User[]>([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const fetchUsers = useCallback(async () => {
    try {
      
      setLoading(true);
      setError(null);

      const data = await apiFetch<User[]>("/admin/users");
      setUsers(data);
      
    } catch {
      setError("유저 목록을 불러오지 못했습니다.");
    } finally {
      setLoading(false);
    }
  }, []);

  useEffect(() => {
    if (authLoading) return;
    if (!canRead) return;
    fetchUsers();
  }, [authLoading, canRead, fetchUsers]);

  const deleteUser = async (id: number) => {
    if (!confirm("정말 삭제하시겠습니까?")) return;

    try { setActionLoading(id);
      await apiFetch(`/admin/users/${id}`, { method: "DELETE" });
      setUsers((prev) => prev.filter((u) => u.id !== id));
    } catch {
      alert("삭제 실패");
    } finally{ setActionLoading(null);
    }
  };

  const changeStatus = async (id: number, status: UserStatus) => {
    try { setStatusLoading(id);
      await apiFetch(`/admin/users/${id}/status`, {
        method: "PATCH",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ status }),
      });

      setUsers((prev) =>
        prev.map((u) => (u.id === id ? { ...u, status } : u))
      );
    } catch {
      alert("상태 변경 실패");
    } finally { setStatusLoading(null);
    }
  };

  if (authLoading || loading) {
    return <div className={styles.loading}>로딩 중...</div>;
  }

  if (!canRead) {
    return <div className={styles.denied}>USER_READ 권한이 없습니다</div>;
  }

  if (error) {
    return <div className={styles.error}>{error}</div>;
  }

  return (
    <div className={styles.container}>
      <h1 className={styles.title}>User Admin</h1>

      <div className={styles.table}>
        <div className={styles.header}>
          <div>ID</div>
          <div>Username</div>
          <div>Status</div>
          <div>Permissions</div>
          {(canUpdate || canDelete) && <div>Actions</div>}
        </div>

        {users.map((user) => (
          <div key={user.id} className={styles.row}>
            <div>{user.id}</div>
            <div>{user.username}</div>

            <div>
              <span className={`${styles.status} ${styles[user.status.toLowerCase()]}`}>
                {user.status}
              </span>
            </div>

            <div className={styles.permissions}>
              {user.permissions?.join(", ") || "-"}
            </div>

            {(canUpdate || canDelete) && (
              <div className={styles.actions}>
                {canUpdate && (
                  <select
                    value={user.status}
                    disabled={statusLoading === user.id}
                    onChange={(e) =>
                      changeStatus(user.id, e.target.value as UserStatus)
                    }
                  >
                    <option value="ACTIVE">ACTIVE</option>
                    <option value="INACTIVE">INACTIVE</option>
                    <option value="BLOCKED">BLOCKED</option>
                  </select>
                )}

                {canDelete && (
                  <button
                    className={styles.deleteBtn}
                    onClick={() => deleteUser(user.id)}
                    disabled={actionLoading === user.id}
                  >
                    Delete
                  </button>
                )}
              </div>
            )}
          </div>
        ))}
      </div>
    </div>
  );
}
