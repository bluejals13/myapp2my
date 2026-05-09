import { BrowserRouter, Routes, Route } from 'react-router-dom'

import Layout from './layout/Layout'
import EmptyLayout from './layout/EmptyLayout'

import ProtectedRoute from './components/ProtectedRoute'

import Main from './pages/Main'
import Home from './pages/Home'
import About from './pages/About'
import Contact from './pages/Contact'
import Dashboard from './pages/Dashboard'
import Login from './pages/Login'

export default function App() {
  return (
    <BrowserRouter>
      <Routes>

        {/* Header 포함 레이아웃 */}
        <Route element={<Layout />}>

          <Route path="/" element={<Main />} />

          <Route path="/main" element={<Main />} />
          <Route path="/home" element={<Home />} />
          <Route path="/about" element={<About />} />
          <Route path="/contact" element={<Contact />} />

          <Route path="/dashboard" element={
              <ProtectedRoute>
                <Dashboard />
              </ProtectedRoute>} />

        </Route>

        {/* 헤더 없는 영역 */}
        <Route element={<EmptyLayout />}>
          <Route path="/login" element={<Login />} />
        </Route>

      </Routes>
    </BrowserRouter>
  )
}
