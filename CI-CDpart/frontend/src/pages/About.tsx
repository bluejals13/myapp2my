import { useEffect, useState } from "react";
import { apiWithAuth } from "../auth/auth.interceptor";
import { useAuth } from "../auth/AuthContext.tsx";

type MeResponse = {
  id: number;
  username: string;
  roles: string[];
  permissions: string[];
};

export default function About() {
  const { token, user } = useAuth();

  const [me, setMe] = useState<MeResponse | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchMe = async () => {
      try {
        setLoading(true);

        const res = await apiWithAuth<MeResponse>("/api/users/me");
        setMe(res);
      } catch (e: any) {
        setError(e?.message ?? "유저 정보를 불러오지 못했습니다.");
      } finally {
        setLoading(false);
      }
    };

    fetchMe();
  }, []);

  if (loading) {
    return <div>로딩 중...</div>;
  }

  if (error) {
    return <div style={{ color: "red" }}>{error}</div>;
  }

  return (
    <div style={{ padding: 20 }}>
      <h1>RBAC Debug Page</h1>

      <h2>👤 User Info</h2>
      <pre>{JSON.stringify(user, null, 2)}</pre>

      <h2>🔐 API /me Response</h2>
      <pre>{JSON.stringify(me, null, 2)}</pre>

      <h2>🧩 Roles</h2>
      <ul>
        {me?.roles?.map((role) => (
          <li key={role}>{role}</li>
        ))}
      </ul>

      <h2>🛡 Permissions</h2>
      <ul>
        {me?.permissions?.map((perm) => (
          <li key={perm}>{perm}</li>
        ))}
      </ul>

      <h2>🧪 Token (debug only)</h2>
      <pre style={{ wordBreak: "break-all" }}>{token}</pre>
    </div>
  );
}
