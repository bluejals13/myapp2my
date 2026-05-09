import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { apiFetch } from '../api'

export default function Login() {
  const [username, setUsername] = useState('')
  const [password, setPassword] = useState('')

  const navigate = useNavigate()

  const login = async () => {
    try {

      const res = await apiFetch('/api/auth/login', {
        method: 'POST',
        body: JSON.stringify({
          username,
          password,
        }),
      })

      if (!res.ok) {
        alert('로그인 실패')
        return
      }

      const token = await res.text()

      localStorage.setItem('token', token)

      alert('로그인 성공')

      navigate('/home')

    } catch (err) {
      console.error(err)
      alert('서버 오류')
    }
  }

  return (
    <div>
      <h2>로그인</h2>

      <form
        onSubmit={(e) => {
          e.preventDefault()
          login()
        }}
      >
        <input
          type="text"
          placeholder="아이디"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
        />

        <br />

        <input
          type="password"
          placeholder="비밀번호"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />

        <br />

        <button type="submit">로그인</button>
      </form>
    </div>
  )
}
