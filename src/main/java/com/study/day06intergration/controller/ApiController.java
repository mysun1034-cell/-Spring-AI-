package com.study.day06intergration.controller;

import com.study.day06intergration.dto.StreamChunk;
import com.study.day06intergration.service.ChatService;
import com.study.day06intergration.service.HelpdeskService;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class ApiController {
    private final ChatService chatService;
    private final HelpdeskService helpdeskService;

    public ApiController(ChatService chatService, HelpdeskService helpdeskService) {
        this.chatService = chatService;
        this.helpdeskService = helpdeskService;
    }

    @GetMapping("/api/stream-console")
    public Flux<String> streamConsole(@RequestParam String question) {
        return chatService.askStream(question)
                .doOnNext(token -> System.out.print(token))
                .doOnComplete(() -> System.out.print(" [stream complete]"));
        // 콘솔에서 확인
    }

    // 토큰이 도착하는대로 흘려 보냄.
    // 브라우저가 EventSOurce.로 소비하도록
    @GetMapping("/api/stream")
    public Flux<ServerSentEvent<StreamChunk>> stream(@RequestParam String question) {
        // 각 토큰들이 StreamChunk로 감싸진 JSON으로 직렬화
        Flux<ServerSentEvent<StreamChunk>> token = chatService.askStream(question)
                .map(chunk -> ServerSentEvent.builder(new StreamChunk(chunk)).build());
        // 완료신호. SSE가 끝난다. 연결을 닫음. 무한 재연결을 방지.
        Flux<ServerSentEvent<StreamChunk>> done = Mono.just(ServerSentEvent.<StreamChunk>builder(new StreamChunk("")).event("done").build())
                .flux();
        return token.concatWith(done);
    }

}
