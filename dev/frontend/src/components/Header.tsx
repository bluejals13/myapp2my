import { Link, useNavigate } from 'react-router-dom'

export default function Header() {

  const navigate = useNavigate()
  const token = localStorage.getItem('token')

  const logout = () => {
    localStorage.removeItem('token')
    navigate('/login')
  }

  return (
    <header style={{ padding: 12, borderBottom: '1px solid #ddd' }}>
      <nav style={{ display: 'flex', gap: 12 }}>
        <Link to="/main">Main</Link>
        <Link to="/home">Home</Link>
        <Link to="/about">About</Link>
        <Link to="/contact">Contact</Link>

        {token ? (
          <>
            <Link to="/dashboard">Dashboard</Link>

            <button onClick={logout}>
                                로그아웃
            </button>
          </>
        ) : (
          <Link to="/login">Login</Link>
        )}

      </nav>
    </header>
  )
}
