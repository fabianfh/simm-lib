/*
 * Copyright (c) 2021 AcadiaSoft, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.acadiasoft.im.simm.engine.margin;

import com.acadiasoft.im.base.margin.SingleMargin;
import com.acadiasoft.im.simm.config.SimmConfig;
import com.acadiasoft.im.simm.model.ConcentrationRiskGroup;
import com.acadiasoft.im.simm.model.Sensitivity;
import com.acadiasoft.im.simm.model.imtree.identifiers.WeightingClass;
import com.acadiasoft.im.simm.model.param.SimmRiskWeight;

import java.math.BigDecimal;
import java.util.List;

public class WeightingMargin extends SingleMargin {

  private static final String LEVEL = "7.WeightedSensitivity";

  private WeightingMargin(WeightingClass weightingClass, BigDecimal margin) {
    super(LEVEL, weightingClass, margin);
  }

  public static WeightingMargin calculate(WeightingClass weightingClass, List<Sensitivity> sensitivities,
                                          ConcentrationRiskGroup concentrationRiskClass, SimmConfig config) {
    BigDecimal nettedAmount = sensitivities.stream()
      .map(sensitivity -> sensitivity.getAmountUsd(config.fxRate()))
      .reduce(BigDecimal.ZERO, BigDecimal::add);
    BigDecimal riskWeight = SimmRiskWeight.get(weightingClass.getSensitivityClass(), weightingClass, config);
    BigDecimal concentrationRisk = concentrationRiskClass.getConcentrationRisk();
    return new WeightingMargin(weightingClass, riskWeight.multiply(nettedAmount).multiply(concentrationRisk));
  }
}
