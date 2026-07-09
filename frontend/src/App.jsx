import { useEffect, useRef, useState } from 'react'
import './App.css'

// 백엔드(Spring AI, 8080)와 프론트(Vite, 5173)는 다른 출처다 → 백엔드에 CORS 설정이 있어야 호출된다.
const API = import.meta.env.VITE_API_BASE ?? ''

export default function App() {
  const [messages, setMessages] = useState([])   // {role:'user'|'assistant', text}
  const [input, setInput] = useState('')
  const [status, setStatus] = useState('idle')    // 'idle' | 'loading' | 'streaming'
  const [streaming, setStreaming] = useState('')   // 지금 흘러들어오는 답변(아직 messages에 확정 전)
  const esRef = useRef(null)
  const bottomRef = useRef(null)

  // conversationId를 localStorage에 고정한다 → 새로고침해도 같은 대화로 이어지고 history가 복원된다.
  const [conversationId] = useState(() => {
    let id = localStorage.getItem('conversationId')
    if (!id) { id = 'web-' + Date.now(); localStorage.setItem('conversationId', id) }
    return id
  })

  // 마운트 시: 이전 대화를 DB에서 불러와 화면 복원 (chat history)
  useEffect(() => {
    fetch(`${API}/api/history?conversationId=${encodeURIComponent(conversationId)}`)
      .then((r) => r.json())
      .then(setMessages)
      .catch(() => {})
  }, [conversationId])

  useEffect(() => { bottomRef.current?.scrollIntoView({ behavior: 'smooth' }) }, [messages, streaming])

  function send() {
    const q = input.trim()
    if (!q || status !== 'idle') return
    setMessages((m) => [...m, { role: 'user', text: q }])
    setInput('')
    setStatus('loading')
    setStreaming('')

    // EventSource로 SSE 스트림을 연다(GET, 쿼리 파라미터). 브라우저 내장이라 별도 라이브러리가 없다.
    const url = `${API}/api/chat/stream?question=${encodeURIComponent(q)}&conversationId=${encodeURIComponent(conversationId)}`
    const es = new EventSource(url)
    esRef.current = es
    let acc = ''

    es.onmessage = (e) => {
      setStatus('streaming')
      acc += JSON.parse(e.data).text   // 각 청크는 {"text":"..."} JSON → 공백·줄바꿈 보존
      setStreaming(acc)
    }
    // 서버가 보내는 완료 신호 → 반드시 close() 해야 EventSource가 무한 재연결하지 않는다.
    es.addEventListener('done', () => {
      setMessages((m) => [...m, { role: 'assistant', text: acc }])
      setStreaming('')
      setStatus('idle')
      es.close()
    })
    es.onerror = () => {
      es.close()
      setStatus('idle')
      if (!acc) setMessages((m) => [...m, { role: 'assistant', text: '⚠ 응답을 받지 못했습니다. 백엔드(8080)가 켜져 있는지 확인하세요.' }])
    }
  }

  function resetConversation() {
    esRef.current?.close()
    localStorage.removeItem('conversationId')
    location.reload()
  }

  return (
    <div className="app">
      <header>
        <h1>AI 사내 헬프데스크</h1>
        <button onClick={resetConversation} title="새 대화 시작">새 대화</button>
      </header>

      <div className="chat">
        {messages.map((m, i) => (
          <div key={i} className={`msg ${m.role}`}>{m.text}</div>
        ))}
        {status === 'loading' && <div className="msg assistant dim">답변 준비 중…</div>}
        {status === 'streaming' && <div className="msg assistant">{streaming}<span className="caret" /></div>}
        <div ref={bottomRef} />
      </div>

      <div className="composer">
        <input
          value={input}
          onChange={(e) => setInput(e.target.value)}
          onKeyDown={(e) => e.key === 'Enter' && send()}
          placeholder="문의를 입력하세요 (예: C001 고객 환불 규정 알려줘)"
          disabled={status !== 'idle'}
        />
        <button onClick={send} disabled={status !== 'idle'}>
          {status === 'idle' ? '보내기' : '응답 중…'}
        </button>
      </div>
    </div>
  )
}
