package com.study.day05toolmcp.tool;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CompanyRuleTools {

    private static final Map<String, String> RULES = Map.of(
            "야간돌봄", "야간에는 침상 주변 장애물을 치우고, 화장실 이동 전 반드시 조명을 켠 뒤 동행합니다.",
            "복약관리", "복약 여부는 시간, 약 종류, 특이 반응을 함께 기록하고 누락 시 보호자 또는 담당자에게 공유합니다.",
            "식사보조", "식사 전 자세를 바로잡고, 삼킴 곤란이나 식사량 감소가 보이면 기록 후 보호자에게 공유합니다.",
            "이동지원", "이동 전 보행 보조기구 위치를 확인하고, 일어서기 전 어지러움 여부를 먼저 확인합니다.",
            "응급연락", "낙상, 호흡곤란, 의식 저하, 흉통은 즉시 119와 보호자에게 연락하고 상황을 기록합니다.",
            "보호자상담", "보호자에게는 확인된 사실과 관찰 내용을 구분해 전달하고, 의학적 판단은 전문기관 상담을 안내합니다.");

    @Tool(description = "돌봄 주제별 장기요양 상담/케어 주의사항을 조회한다. 주제: 야간돌봄, 복약관리, 식사보조, 이동지원, 응급연락, 보호자상담")
    String getCareRule(
            @ToolParam(description = "돌봄 규칙 주제. 예: 야간돌봄, 복약관리, 식사보조, 이동지원, 응급연락, 보호자상담") String topic
    ) {
        return RULES.getOrDefault(topic, "해당 주제의 돌봄 규칙은 등록되어 있지 않습니다. 확인된 자료나 담당자에게 추가 확인이 필요합니다.");
    }

}
