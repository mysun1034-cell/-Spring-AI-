package com.study.day05toolmcp.tool;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CustomerTools {

    public record RecipientProfile(
            String recipientId,
            String name,
            String careLevel,
            String mobility,
            String mealSupport,
            String medication,
            String nightCareNote,
            String guardianContactPriority) {
    }

    private static final Map<String, RecipientProfile> RECIPIENTS = Map.of(
            "R001", new RecipientProfile("R001", "김영자", "장기요양 3등급", "보행 보조 필요",
                    "부드러운 음식 선호, 식사 속도 관찰", "아침/저녁 복약 확인 필요",
                    "야간 화장실 이동 시 낙상 주의", "높음"),
            "R002", new RecipientProfile("R002", "박춘식", "장기요양 4등급", "실내 이동 가능",
                    "식사량 감소 관찰", "점심 약 복용 여부 확인", "수면 중 기침 관찰", "보통"),
            "R003", new RecipientProfile("R003", "이순례", "장기요양 2등급", "휠체어 이동",
                    "식사 전 자세 보조 필요", "복약 거부 가능성 있어 보호자 공유",
                    "체위 변경과 호출벨 위치 확인", "매우 높음"));

    @Tool(description = "수급자 ID로 장기요양 등급, 이동, 식사, 복약, 야간 돌봄 주의사항을 조회한다")
    RecipientProfile getRecipientProfile(
            @ToolParam(description = "수급자 ID. 예: R001, R002, R003") String recipientId) {
        RecipientProfile profile = RECIPIENTS.get(recipientId);
        if (profile == null) {
            return new RecipientProfile(recipientId, "미등록 수급자", "미확인", "미확인",
                    "미확인", "미확인", "미확인", "확인 필요");
        }
        return profile;
    }
}

