import { useEffect, useState } from "react";
import { apiFetch } from "../../api";
import { useAuth } from "../../auth/AuthContext";
import styles from "./permission.module.css";

type Permission = {
  id: number;
  name: string;
  description: string;
};

export default function PermissionPage() {
  const { hasPermission, isLoading } = useAuth();

  const canView = hasPermission("PERMISSION_READ");

  const [permissions, setPermissions] = useState<Permission[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  const fetchPermissions = async () => {    // 퍼미션 검증 로직
    try { setLoading(true);
      setError(null);
      const data = await apiFetch<Permission[]>(
          "/api/admin/permissions"
      );
      setPermissions(data);
    } catch {
      setError("권한 목록을 불러오지 못했습니다.");
    } finally {
      setLoading(false);
    }
  };


  useEffect(() => {      // 트리거 만 시도
    if (!canView) {
      setLoading(false);
      return;
    } 
    
    fetchPermissions();
  }, [canView]);

  if (isLoading || loading) {
    return <div className={styles.loading}>로딩 중...</div>;
  }

  if (!canView) {
    return (
      <div className={styles.denied}>
        🚫 PERMISSION_READ 권한이 없습니다
      </div>
    );
  }

  if (error) {
    return <div className={styles.error}>{error}</div>;
  }

  return (
    <div className={styles.container}>
      <h1 className={styles.title}>Permission 관리</h1>

      <div className={styles.table}>
        <div className={styles.headerRow}>
          <div>ID</div>
          <div>Name</div>
          <div>Description</div>
        </div>

        {permissions.map((p) => (
          <div key={p.id} className={styles.row}>
            <div>{p.id}</div>
            <div className={styles.code}>{p.name}</div>
            <div>{p.description}</div>
          </div>
        ))}
      </div>
    </div>
  );
}
